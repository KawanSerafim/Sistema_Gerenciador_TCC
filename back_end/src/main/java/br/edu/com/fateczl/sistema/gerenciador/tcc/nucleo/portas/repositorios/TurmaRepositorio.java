package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Curso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Turma;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.Turno;

public interface TurmaRepositorio {
    Turma salvar(Turma turma);

    Turma buscarPorCursoDisciplinaTurnoAnoSemestre(
            Curso curso,
            Disciplina disciplina,
            Turno turno,
            Integer ano,
            Integer semestre
    );
}