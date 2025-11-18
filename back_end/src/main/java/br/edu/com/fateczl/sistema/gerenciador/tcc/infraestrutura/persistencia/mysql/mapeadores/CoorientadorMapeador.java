package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.mapeadores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.CoorientadorModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Coorientador;
import org.springframework.stereotype.Component;

@Component
public class CoorientadorMapeador {
    public CoorientadorModelo paraModelo(Coorientador dominio) {
        if(dominio == null) return null;

        var coorientadorModelo = new CoorientadorModelo();
        coorientadorModelo.setId(dominio.getId());
        coorientadorModelo.setNome(dominio.getNome());
        coorientadorModelo.setOrigem(dominio.getOrigem());
        coorientadorModelo.setCargoProfessor(dominio.getCargoProfessor());

        return coorientadorModelo;
    }

    public Coorientador paraDominio(CoorientadorModelo modelo) {
        if(modelo == null) return null;

        var coorientador = new Coorientador();
        coorientador.setId(modelo.getId());
        coorientador.setNome(modelo.getNome());
        coorientador.setOrigem(modelo.getOrigem());
        coorientador.setCargoProfessor(modelo.getCargoProfessor());

        return coorientador;
    }
}