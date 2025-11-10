package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.spring;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.CursoModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.ProfessorModelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CursoRepositorioSpring
        extends JpaRepository<CursoModelo, Long> {
    Optional<CursoModelo> findByNome(String nome);

    Optional<CursoModelo> findByCoordenador(ProfessorModelo coordenador);
}