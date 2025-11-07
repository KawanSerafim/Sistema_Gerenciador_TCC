package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.mapeadores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.ParametrosCursoModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .ParametrosCurso;
import org.springframework.stereotype.Component;

@Component
public class ParametrosCursoMapeador {
    public ParametrosCursoModelo paraModelo(ParametrosCurso dominio) {
        if(dominio == null) return null;

        var parametrosCursoModelo = new ParametrosCursoModelo();
        parametrosCursoModelo.setDisciplinas(dominio.getDisciplinas());
        parametrosCursoModelo.setTurnos(dominio.getTurnos());

        return parametrosCursoModelo;
    }

    public ParametrosCurso paraDominio(ParametrosCursoModelo modelo) {
        if(modelo == null) return null;

        var parametrosCurso = new ParametrosCurso();
        parametrosCurso.setDisciplinas(modelo.getDisciplinas());
        parametrosCurso.setTurnos(modelo.getTurnos());

        return parametrosCurso;
    }
}