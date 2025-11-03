package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Professor;

import java.util.Optional;

public interface ProfessorRepositorio {
    Professor salvar(Professor professor);

    Optional<Professor> buscarPorEmail(String email);
}