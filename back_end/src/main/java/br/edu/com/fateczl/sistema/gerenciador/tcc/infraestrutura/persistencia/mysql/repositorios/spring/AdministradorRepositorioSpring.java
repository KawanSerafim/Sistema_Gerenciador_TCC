package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.spring;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia.mysql.modelos.AdministradorModelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministradorRepositorioSpring
        extends JpaRepository<AdministradorModelo, Integer> {
    Optional<AdministradorModelo> findByContaUsuarioEmail(String email);
}