package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Curso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Professor;

import java.util.Optional;

public interface CursoRepositorio {
    Curso salvar(Curso curso);

    Optional<Curso> buscarPorNome(String nome);

    Optional<Curso> buscarPorCoordenador(Professor coordenador);
}