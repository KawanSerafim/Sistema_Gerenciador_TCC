package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.spring;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.CoorientadorModelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoorientadorRepositorioSpring
        extends JpaRepository<CoorientadorModelo, Long> {
    Optional<CoorientadorModelo> findByNomeAndOrigem(
            String nome,
            String origem
    );
}