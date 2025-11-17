package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.spring;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.AlunoModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.GrupoTccModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.TurmaModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GrupoTccRepositorioSpring
        extends JpaRepository<GrupoTccModelo, Long> {
    @Query("SELECT g FROM GrupoTccModelo g JOIN g.alunos a WHERE g.turma = "
            + ":turma AND a IN :alunos")
    List<GrupoTccModelo> findByAlunosAndTurma(
            @Param("alunos") List<AlunoModelo> alunos,
            @Param("turma") TurmaModelo turma
    );
}