package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.spring;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.CursoModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.TurmaModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TurmaRepositorioSpring
        extends JpaRepository<TurmaModelo, Integer> {
    Optional<TurmaModelo> findById(Long id);

    Optional<TurmaModelo> findByCursoAndDisciplinaAndTurnoAndAnoAndSemestre(
            CursoModelo curso,
            Disciplina disciplina,
            Turno turno,
            Integer ano,
            Integer semestre
    );
}