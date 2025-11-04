package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.spring;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia.mysql.modelos.ProfessorModelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorRepositorioSpring
        extends JpaRepository<ProfessorModelo, Integer> {
    Optional<ProfessorModelo> findByContaUsuarioEmail(String email);
}