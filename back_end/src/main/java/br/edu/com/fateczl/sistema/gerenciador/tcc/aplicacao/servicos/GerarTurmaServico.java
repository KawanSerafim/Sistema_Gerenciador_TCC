package br.edu.com.fateczl.sistema.gerenciador.tcc.aplicacao.servicos;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso
        .GerarTurmaCaso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Curso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Professor;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Turma;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.StatusContaUsuario;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.Turno;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .CursoRepositorio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .ProfessorRepositorio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .TurmaRepositorio;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GerarTurmaServico implements GerarTurmaCaso {
    private final TurmaRepositorio turmaRepositorio;
    private final CursoRepositorio cursoRepositorio;
    private final ProfessorRepositorio professorRepositorio;

    public GerarTurmaServico(
            TurmaRepositorio turmaRepositorio,
            CursoRepositorio cursoRepositorio,
            ProfessorRepositorio professorRepositorio
    ) {
        this.turmaRepositorio = turmaRepositorio;
        this.cursoRepositorio = cursoRepositorio;
        this.professorRepositorio = professorRepositorio;
    }

    @Override
    public Saida executar(Entrada entrada) {
        var coordenadorCurso = pegarCoordenadorCurso(
                entrada.emailCoordenador()
        );
        var professorTg = pegarProfessorTg(entrada.matriculaProfessorTg());

        var curso = pegarCurso(coordenadorCurso);

        var turma = pegarTurma(
                curso,
                entrada.disciplina(),
                entrada.turno(),
                professorTg,
                entrada.ano(),
                entrada.semestre()
        );

        var turmaSalva = turmaRepositorio.salvar(turma);

        return new Saida(
                turmaSalva.getId(),
                curso.getNome(),
                turmaSalva.getDisciplina(),
                turmaSalva.getTurno(),
                professorTg.getNome(),
                turmaSalva.getAno(),
                turmaSalva.getSemestre()
        );
    }

    private Curso pegarCurso(Professor coordenadorCurso) {
        return cursoRepositorio.buscarPorCoordenador(coordenadorCurso)
                .orElseThrow(() -> new ExcecaoDominio(
                        CodigoErro.GN_001_REGISTRO_NAO_ENCONTRADO,
                        "[Curso]: Não encontrado. Nenhuma entidade "
                        + "localizada com o critério: [coordenador] = '["
                        + coordenadorCurso.getNome() + "]'."
                ));
    }

    private Professor pegarCoordenadorCurso(String email) {
        return professorRepositorio.buscarPorEmail(email)
                .orElseThrow(() -> new ExcecaoDominio(
                        CodigoErro.GN_001_REGISTRO_NAO_ENCONTRADO,
                        "[Professor]: Não encontrado. Nenhuma entidade "
                        + "localizada com o critério: [email] = '[" + email
                        + "]'."
                ));
    }

    private Professor pegarProfessorTg(String matricula) {
        Professor professor = professorRepositorio
                .buscarPorMatricula(matricula)
                .orElseThrow(() -> new ExcecaoDominio(
                        CodigoErro.GN_001_REGISTRO_NAO_ENCONTRADO,
                        "[Professor]: Não encontrado. Nenhuma entidade "
                        + "localizada com o critério: [matrícula] = '["
                        + matricula + "]'."
                ));

        if(!professor.podeSerProfessorTg()) {
            throw new ExcecaoDominio(
                    CodigoErro.RN_001_ESTADO_INVALIDO_PARA_ACAO,
                    "[Professor] (ID: [" + professor.getId() + "]): Ação "
                    + "'[Ser cadastrado numa turma]' falhou devido a cargo "
                    + "inválido. (CargoAtual: '[" + professor.getCargo() + "]',"
                    + "Esperado: '[PROFESSOR_TG]')"
            );
        }

        if(professor.getStatusContaUsuario() != StatusContaUsuario.ATIVO) {
            throw new ExcecaoDominio(
                    CodigoErro.RN_001_ESTADO_INVALIDO_PARA_ACAO,
                    "[Professor] (ID: [" + professor.getId() + "]): Ação "
                    + "'[Ser cadastrado numa turma]' falhou devido a estado de "
                    + "conta inválido. (EstadoAtual: '["
                    + professor.getStatusContaUsuario() + "]', Esperado: "
                    + "'[ATIVO]')"
            );
        }

        return professor;
    }

    private Turma pegarTurma(
            Curso curso,
            Disciplina disciplina,
            Turno turno,
            Professor professorTg,
            Integer ano,
            Integer semestre
    ) {
        Optional<Turma> turmaOpt = turmaRepositorio
                .buscarPorCursoEDisciplinaETurnoEAnoESemestre(
                        curso,
                        disciplina,
                        turno,
                        ano,
                        semestre
                );

        if(turmaOpt.isPresent()) {
            throw new ExcecaoDominio(
                    CodigoErro.RN_002_REGISTRO_DUPLICADO,
                    "[Turma]: Falha de unicidade. Já existe um registro com "
                    + "[curso] = '[" + curso.getNome() + "]', [disciplina] = "
                    + "'["+ disciplina +"]', [turno] = '[" + turno +"]', "
                    + "[ano] = '[" + ano + "]', [semestre] = '[" + semestre
                    + "]'."
            );
        }

        return new Turma(
                curso,
                disciplina,
                turno,
                professorTg,
                ano,
                semestre
        );
    }
}