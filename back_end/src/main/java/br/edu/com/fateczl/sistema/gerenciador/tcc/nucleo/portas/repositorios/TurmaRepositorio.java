package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Curso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Turma;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.Turno;

import java.util.Optional;

public interface TurmaRepositorio {
    Turma salvar(Turma turma);

    Optional<Turma> buscarPorCursoEDisciplinaETurnoEAnoESemestre(
            Curso curso,
            Disciplina disciplina,
            Turno turno,
            Integer ano,
            Integer semestre
    );
}