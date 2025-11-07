package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.mapeadores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.TurmaModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Turma;
import org.springframework.stereotype.Component;

@Component
public class TurmaMapeador {
    private final CursoMapeador cursoMapeador;
    private final ProfessorMapeador professorMapeador;

    public TurmaMapeador(
            CursoMapeador cursoMapeador,
            ProfessorMapeador professorMapeador
    ) {
        this.cursoMapeador = cursoMapeador;
        this.professorMapeador = professorMapeador;
    }

    public TurmaModelo paraModelo(Turma dominio) {
        if(dominio == null) return null;

        var turmaModelo = new TurmaModelo();
        turmaModelo.setId(dominio.getId());
        turmaModelo.setCurso(
                cursoMapeador.paraModelo(dominio.getCurso())
        );
        turmaModelo.setDisciplina(dominio.getDisciplina());
        turmaModelo.setTurno(dominio.getTurno());
        turmaModelo.setProfessorTg(
                professorMapeador.paraModelo(dominio.getProfessorTg())
        );
        turmaModelo.setAno(dominio.getAno());
        turmaModelo.setSemestre(dominio.getSemestre());

        return turmaModelo;
    }

    public Turma paraDominio(TurmaModelo modelo) {
        if(modelo == null) return null;

        var turma = new Turma();
        turma.setId(modelo.getId());
        turma.setCurso(
                cursoMapeador.paraDominio(modelo.getCurso())
        );
        turma.setDisciplina(modelo.getDisciplina());
        turma.setTurno(modelo.getTurno());
        turma.setProfessorTg(
                professorMapeador.paraDominio(modelo.getProfessorTg())
        );
        turma.setAno(modelo.getAno());
        turma.setSemestre(modelo.getSemestre());

        return turma;
    }
}