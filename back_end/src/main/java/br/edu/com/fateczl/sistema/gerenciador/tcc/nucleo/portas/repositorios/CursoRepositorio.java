package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Curso;

import java.util.Optional;

public interface CursoRepositorio {
    Curso salvar(Curso curso);

    Optional<Curso> buscarNome(String nome);
}