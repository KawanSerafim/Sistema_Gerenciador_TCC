package br.edu.com.fateczl.sistema.gerenciador.tcc.aplicacao.servicos;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso.GerarCursoCaso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Curso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .ParametrosCurso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Professor;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .CursoRepositorio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .ProfessorRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class GerarCursoServico implements GerarCursoCaso {
    private final CursoRepositorio cursoRepositorio;
    private final ProfessorRepositorio professorRepositorio;

    public GerarCursoServico(
            CursoRepositorio cursoRepositorio,
            ProfessorRepositorio professorRepositorio
    ) {
        this.cursoRepositorio = cursoRepositorio;
        this.professorRepositorio = professorRepositorio;
    }

    @Override
    @Transactional
    public Saida executar(Entrada entrada) {
        var coordenador = pegarCoordenador(entrada.matriculaCoordenador());

        var parametros = new ParametrosCurso(
                entrada.turnos(),
                entrada.disciplinas(),
                entrada.maxAlunosGrupo()
        );

        var curso = pegarCurso(entrada, parametros, coordenador);
        var cursoSalvo = cursoRepositorio.salvar(curso);

        return new Saida(
                cursoSalvo.getId(),
                cursoSalvo.getNome(),
                cursoSalvo.getTurnos(),
                cursoSalvo.getDisciplinas(),
                cursoSalvo.getMaxAlunosGrupo(),
                coordenador.getNome(),
                coordenador.getMatricula()
        );
    }

    private Professor pegarCoordenador(String matricula) {
        Professor professor = professorRepositorio.buscarPorMatricula(matricula)
                .orElseThrow(() -> new ExcecaoDominio(
                        CodigoErro.GN_001_REGISTRO_NAO_ENCONTRADO,
                        "Professor: Não encontrado. Nenhuma entidade localizada"
                        + " com o critério: [Matrícula] = '[" + matricula
                        + "]'."
                ));

        if(!professor.podeSerCoordenadorCurso()) {
            throw new ExcecaoDominio(
                    CodigoErro.RN_001_ESTADO_INVALIDO_PARA_ACAO,
                    "[Professor] (ID: [" + professor.getId() + "]): Ação "
                    + "'[Ser cadastrado em curso]' falhou devido a estado "
                    + "inválido. (EstadoAtual: '[" + professor.getCargo()
                    + "]', Esperado: '[COORDENADOR_CURSO]')"
            );
        }

        return professor;
    }

    private Curso pegarCurso(
            Entrada entrada,
            ParametrosCurso parametros,
            Professor coordenador
    ) {
        Optional<Curso> cursoOpt = cursoRepositorio
                .buscarPorNome(entrada.nome());

        if(cursoOpt.isPresent()) {
            throw new ExcecaoDominio(
                    CodigoErro.RN_002_REGISTRO_DUPLICADO,
                    "Curso: Falha de unicidade. Já existe um registro com "
                    + "[Nome] = '[" + entrada.nome() +"]'."
            );
        }

        return new Curso(
                entrada.nome(),
                parametros,
                coordenador
        );
    }
}