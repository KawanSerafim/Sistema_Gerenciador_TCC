package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Coorientador;

import java.util.Optional;

public interface CoorientadorRepositorio {
    Coorientador salvar(Coorientador coorientador);

    Optional<Coorientador> buscarPorNomeEOrigem(String nome, String origem);
}