package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.mapeadores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.CursoModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Curso;
import org.springframework.stereotype.Component;

@Component
public class CursoMapeador {
    private final ParametrosCursoMapeador parametrosCursoMapeador;
    private final ProfessorMapeador professorMapeador;

    public CursoMapeador(
            ParametrosCursoMapeador parametrosCursoMapeador,
            ProfessorMapeador professorMapeador
    ) {
        this.parametrosCursoMapeador = parametrosCursoMapeador;
        this.professorMapeador = professorMapeador;
    }

    public CursoModelo paraModelo(Curso dominio) {
        if(dominio == null) return null;

        var cursoModelo = new CursoModelo();
        cursoModelo.setId(dominio.getId());
        cursoModelo.setNome(dominio.getNome());
        cursoModelo.setParametros(
                parametrosCursoMapeador.paraModelo(dominio.getParametros())
        );
        cursoModelo.setCoordenador(
                professorMapeador.paraModelo(dominio.getCoordenador())
        );

        return cursoModelo;
    }

    public Curso paraDominio(CursoModelo modelo) {
        if(modelo == null) return null;

        var curso = new Curso();
        curso.setId(modelo.getId());
        curso.setNome(modelo.getNome());
        curso.setParametros(
                parametrosCursoMapeador.paraDominio(modelo.getParametros())
        );
        curso.setCoordenador(
                professorMapeador.paraDominio(modelo.getCoordenador())
        );

        return curso;
    }
}