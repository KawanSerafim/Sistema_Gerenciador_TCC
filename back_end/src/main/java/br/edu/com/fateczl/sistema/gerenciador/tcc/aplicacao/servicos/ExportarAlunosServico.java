package br.edu.com.fateczl.sistema.gerenciador.tcc.aplicacao.servicos;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso
        .ExportarAlunosCaso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Aluno;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Turma;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas
        .LeitorArquivoDadosAlunosPorta;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .AlunoRepositorio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .ProfessorRepositorio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .TurmaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ExportarAlunosServico implements ExportarAlunosCaso {
    private final AlunoRepositorio alunoRepositorio;
    private final ProfessorRepositorio professorRepositorio;
    private final TurmaRepositorio turmaRepositorio;
    private final LeitorArquivoDadosAlunosPorta leitorArquivo;

    public ExportarAlunosServico(
            AlunoRepositorio alunoRepositorio,
            ProfessorRepositorio professorRepositorio,
            TurmaRepositorio turmaRepositorio,
            LeitorArquivoDadosAlunosPorta leitorArquivo
    ) {
        this.alunoRepositorio = alunoRepositorio;
        this.professorRepositorio = professorRepositorio;
        this.turmaRepositorio = turmaRepositorio;
        this.leitorArquivo = leitorArquivo;
    }

    @Override
    @Transactional
    public Saida executar(Entrada entrada) {
        var turma = pegarTurma(entrada.idTurma());
        var dadosArquivo = leitorArquivo.ler(entrada.arquivoBruto());

        validarProfessorTg(entrada.emailProfessorTg(), turma);
        verificarValidadeArquivo(dadosArquivo, turma);

        List<Aluno> alunosAdicionados = adicionarAlunos(dadosArquivo, turma);

        return new Saida(
                turma.getId(),
                turma.getNomeCurso(),
                turma.getTurno(),
                turma.getDisciplina(),
                turma.getAno(),
                turma.getSemestre(),
                alunosAdicionados
        );
    }

    private Turma pegarTurma(Long id) {
        return turmaRepositorio.buscarPorId(id)
                .orElseThrow(() -> new ExcecaoDominio(
                        CodigoErro.GN_001_REGISTRO_NAO_ENCONTRADO,
                        "[Turma]: Não encontrada. Nenhuma entidade "
                        + "localizada com o critério: [ID] = '[" + id
                        + "]'."
                ));
    }

    private void validarProfessorTg(String email, Turma turma) {
        var professor = professorRepositorio.buscarPorEmail(email)
            .orElseThrow(() -> new ExcecaoDominio(
                    CodigoErro.GN_001_REGISTRO_NAO_ENCONTRADO,
                    "[Professor]: Não encontrado. Nenhuma entidade "
                    + "localizada com o critério: [email] = '[" + email
                    + "]'."
            ));

        if(!Objects.equals(turma.getIdProfessorTg(), professor.getId())) {
            throw new ExcecaoDominio(
                    CodigoErro.AU_001_PERMISSAO_NEGADA,
                    "Autorização: Permissão negada para Usuário (ID: ["
                    + professor.getId() + "]). (Ação: '[Exportar alunos "
                    + "via arquivo]', Recurso: '[Turma] (ID: [" + turma.getId()
                    + "])')"
            );
        }
    }

    private void verificarValidadeArquivo(
            LeitorArquivoDadosAlunosPorta.DadosArquivo dadosArquivo,
            Turma turma
    ) {
        if(!Objects.equals(turma.getAno(), dadosArquivo.ano())) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_007_CAMPO_NAO_SUPORTADO,
                    "[Serviço de Exportar Aluno]: O campo '[ano]' não é "
                    + "suportado [valor do arquivo não bate com a turma do "
                    + "professor de TG]."
            );
        }

        if(!Objects.equals(turma.getSemestre(), dadosArquivo.semestre())) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_007_CAMPO_NAO_SUPORTADO,
                    "[Serviço de Exportar Aluno]: O campo '[semestre]' não é "
                    + "suportado [valor do arquivo não bate com a turma do "
                    + "professor de TG]."
            );
        }

        if(!Objects.equals(turma.getTurno(), dadosArquivo.turno())) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_007_CAMPO_NAO_SUPORTADO,
                    "[Serviço de Exportar Aluno]: O campo '[turno]' não é "
                    + "suportado [valor do arquivo não bate com a turma do "
                    + "professor de TG]."
            );
        }
    }

    private List<Aluno> adicionarAlunos(
            LeitorArquivoDadosAlunosPorta.DadosArquivo arquivo,
            Turma turma
    ) {
        List<Aluno> alunosAdicionados = new ArrayList<>();

        for(var dadosAlunos : arquivo.alunos()) {
            Optional<Aluno> alunosOpt = alunoRepositorio
                    .buscarPorMatricula(dadosAlunos.matricula());

            if(alunosOpt.isPresent()) {
                var alunoJaCadastrado = alunosOpt.get();
                alunoJaCadastrado.matricularEmTurma(turma);

                var alunoSalvo = alunoRepositorio.salvar(alunoJaCadastrado);
                alunosAdicionados.add(alunoSalvo);
            } else {
                var novoAluno = new Aluno(
                        dadosAlunos.name(),
                        dadosAlunos.matricula(),
                        turma
                );

                var alunoSalvo = alunoRepositorio.salvar(novoAluno);
                alunosAdicionados.add(alunoSalvo);
            }
        }
        return alunosAdicionados;
    }
}