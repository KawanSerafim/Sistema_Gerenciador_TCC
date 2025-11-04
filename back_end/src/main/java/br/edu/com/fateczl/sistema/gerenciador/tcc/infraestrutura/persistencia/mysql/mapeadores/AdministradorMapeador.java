package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.mapeadores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.AdministradorModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Administrador;
import org.springframework.stereotype.Component;

@Component
public class AdministradorMapeador {
    private final ContaUsuarioMapeador contaUsuarioMapeador;

    public AdministradorMapeador(
            ContaUsuarioMapeador contaUsuarioMapeador
    ) {
        this.contaUsuarioMapeador = contaUsuarioMapeador;
    }

    public AdministradorModelo paraModelo(Administrador dominio) {
        if(dominio == null) return null;

        var administradorModelo = new AdministradorModelo();
        administradorModelo.setId(dominio.getId());
        administradorModelo.setNome(dominio.getNome());
        administradorModelo.setContaUsuario(
                contaUsuarioMapeador.paraModelo(dominio.getContaUsuario())
        );

        return administradorModelo;
    }

    public Administrador paraDominio(AdministradorModelo modelo) {
        if(modelo == null) return null;

        var administrador = new Administrador();
        administrador.setId(modelo.getId());
        administrador.setNome(modelo.getNome());
        administrador.setContaUsuario(
                contaUsuarioMapeador.paraDominio(modelo.getContaUsuario())
        );

        return administrador;
    }
}