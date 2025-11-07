package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Aluno;

import java.util.Optional;

public interface AlunoRepositorio {
    Aluno salvar(Aluno aluno);

    Optional<Aluno> buscarPorMatricula(String matricula);

    Optional<Aluno> buscarPorEmail(String email);
}