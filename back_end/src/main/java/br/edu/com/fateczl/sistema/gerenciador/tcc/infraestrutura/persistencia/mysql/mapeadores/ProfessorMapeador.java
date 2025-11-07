package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.mapeadores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.ProfessorModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Professor;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapeador {
    private final ContaUsuarioMapeador contaUsuarioMapeador;

    public ProfessorMapeador(
            ContaUsuarioMapeador contaUsuarioMapeador
    ) {
        this.contaUsuarioMapeador = contaUsuarioMapeador;
    }

    public ProfessorModelo paraModelo(Professor dominio) {
        if(dominio == null) return null;

        var professorModelo = new ProfessorModelo();
        professorModelo.setId(dominio.getId());
        professorModelo.setNome(dominio.getNome());
        professorModelo.setMatricula(dominio.getMatricula());
        professorModelo.setCargo(dominio.getCargo());
        professorModelo.setContaUsuario(
                contaUsuarioMapeador.paraModelo(dominio.getContaUsuario())
        );

        return professorModelo;
    }

    public Professor paraDominio(ProfessorModelo modelo) {
        if(modelo == null) return null;

        var professor = new Professor();
        professor.setId(modelo.getId());
        professor.setNome(modelo.getNome());
        professor.setMatricula(modelo.getMatricula());
        professor.setCargo(modelo.getCargo());
        professor.setContaUsuario(
                contaUsuarioMapeador.paraDominio(modelo.getContaUsuario())
        );

        return professor;
    }
}