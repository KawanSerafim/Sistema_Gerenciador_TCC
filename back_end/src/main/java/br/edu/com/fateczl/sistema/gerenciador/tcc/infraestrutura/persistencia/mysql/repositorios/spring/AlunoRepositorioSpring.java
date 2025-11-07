package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.spring;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.AlunoModelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlunoRepositorioSpring
        extends JpaRepository<AlunoModelo, Integer> {
    Optional<AlunoModelo> findByMatricula(String matricula);

    Optional<AlunoModelo> findByContaUsuarioEmail(String email);
}