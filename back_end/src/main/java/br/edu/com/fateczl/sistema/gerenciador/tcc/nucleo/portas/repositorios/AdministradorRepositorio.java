package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Administrador;

import java.util.Optional;

public interface AdministradorRepositorio {
    Administrador salvar(Administrador administrador);

    Optional<Administrador> buscarPorEmail(String email);
}