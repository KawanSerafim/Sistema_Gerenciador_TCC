package br.edu.com.fateczl.sistema.gerenciador.tcc.aplicacao.servicos;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso
        .GerarGrupoTccCaso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Aluno;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Curso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .GrupoTcc;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Turma;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.StatusAluno;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.StatusContaUsuario;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.TipoTcc;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .AlunoRepositorio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .GrupoTccRepositorio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .TurmaRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GerarGrupoTccServico implements GerarGrupoTccCaso {
    private final GrupoTccRepositorio grupoTccRepositorio;
    private final TurmaRepositorio turmaRepositorio;
    private final AlunoRepositorio alunoRepositorio;

    public GerarGrupoTccServico(
            GrupoTccRepositorio grupoTccRepositorio,
            TurmaRepositorio turmaRepositorio,
            AlunoRepositorio alunoRepositorio
    ) {
        this.grupoTccRepositorio = grupoTccRepositorio;
        this.turmaRepositorio = turmaRepositorio;
        this.alunoRepositorio = alunoRepositorio;
    }

    @Override
    @Transactional
    public void executar(Entrada entrada) {
        var turma = pegarTurma(entrada.idTurma());
        var curso = turma.getCurso();

        verificarParametrosCurso(
                entrada.tipoTcc(),
                entrada.disciplina(),
                curso
        );
        var alunos = pegarAlunos(entrada.matriculasAluno(), turma, curso);

        var grupoTcc = pegarGrupoTcc(
                entrada.tema(),
                entrada.tipoTcc(),
                entrada.disciplina(),
                alunos,
                turma
        );

        grupoTccRepositorio.salvar(grupoTcc);
    }

    private Turma pegarTurma(Long id) {
        return turmaRepositorio.buscarPorId(id)
                .orElseThrow(() -> new ExcecaoDominio(
                        CodigoErro.GN_001_REGISTRO_NAO_ENCONTRADO,
                        "[Turma]: Não encontrado. Nenhuma entidade localizada "
                        + "com o critério: [ID] = '[" + id + "]'."
                ));
    }

    private List<Aluno> pegarAlunos(
            List<String> matriculas,
            Turma turma,
            Curso curso
    ) {
        List<Aluno> alunos = new ArrayList<>();
        List<String> nomesAlunos = new ArrayList<>();
        int contador = 0;

        for(String matricula : matriculas) {
            var alunoOpt = alunoRepositorio.buscarPorMatricula(matricula);

            if(alunoOpt.isEmpty()) {
                throw new ExcecaoDominio(
                        CodigoErro.GN_001_REGISTRO_NAO_ENCONTRADO,
                        "[Aluno]: Não encontrado. Nenhuma entidade localizada "
                        + "com o critério: [Matrícula] = '[" + matricula + "]'."
                );
            }

            var aluno = alunoOpt.get();

            if(aluno.getStatus() != StatusAluno.CADASTRADO) {
                throw new ExcecaoDominio(
                        CodigoErro.RN_001_ESTADO_INVALIDO_PARA_ACAO,
                        "[Aluno] (ID: [" + aluno.getId() + "]): Ação '["
                        + "Ser cadastrado em um grupo de TCC]' falhou devido a "
                        + "estado inválido. (EstadoAtual: '["
                        + aluno.getStatus() + "]', Esperado: '[CADASTRADO]')"
                );
            }

            if(aluno.getStatusContaUsuario() != StatusContaUsuario.ATIVO) {
                throw new ExcecaoDominio(
                        CodigoErro.RN_001_ESTADO_INVALIDO_PARA_ACAO,
                        "[Aluno] (ID: [" + aluno.getId() + "]): Ação '["
                        + "Ser cadastrado em um grupo de TCC]' falhou devido a "
                        + "estado inválido. (EstadoAtual: '["
                        + aluno.getStatusContaUsuario() + "]', Esperado: '["
                        + "ATIVO]')"
                );
            }

            contador++;
            alunos.add(alunoOpt.get());
            nomesAlunos.add(alunoOpt.get().getNome());
        }

        verificarQtdAlunos(contador, curso);

        var grupoTccOpt = grupoTccRepositorio.buscarPorAlunosETurma(
                alunos,
                turma
        );

        if(grupoTccOpt.isPresent()) {
            throw new ExcecaoDominio(
                    CodigoErro.RN_002_REGISTRO_DUPLICADO,
                    "[GrupoTcc]: Falha de unicidade. Já existe um registro com "
                    + "[Alunos e Turma] = '[" + nomesAlunos + ", "
                    + turma.getId() + "]'."
            );
        }

        return alunos;
    }

    private void verificarQtdAlunos(int contador, Curso curso) {
        if(!curso.validarQtdAlunosGrupo(contador)) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_007_CAMPO_NAO_SUPORTADO,
                    "[GrupoTcc]: O campo '[Alunos]' não é suportado ["
                    + "A quantidade de alunos excede o permitido]."
            );
        }
    }

    private void verificarParametrosCurso(
            TipoTcc tipo,
            Disciplina disciplina,
            Curso curso
    ) {
        if(!curso.validarTipoTcc(tipo)) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_007_CAMPO_NAO_SUPORTADO,
                    "[GrupoTcc]: O campo '[Tipo de TCC]' não é suportado ["
                    + "O tipo de TCC não é suportado pelo curso]."
            );
        }

        if(!curso.validarDisciplina(disciplina)) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_007_CAMPO_NAO_SUPORTADO,
                    "[GrupoTcc]: O campo '[Disciplina]' não é suportado ["
                    + "A disciplina não é suportada pelo curso]."
            );
        }
    }

    private GrupoTcc pegarGrupoTcc(
            String tema,
            TipoTcc tipoTcc,
            Disciplina disciplina,
            List<Aluno> alunos,
            Turma turma
    ) {
        return new GrupoTcc(
                tema,
                tipoTcc,
                disciplina,
                alunos,
                turma
        );
    }
}