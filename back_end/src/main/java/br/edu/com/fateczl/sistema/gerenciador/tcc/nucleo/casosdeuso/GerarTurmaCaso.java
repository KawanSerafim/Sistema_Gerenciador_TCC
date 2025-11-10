package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.Turno;

public interface GerarTurmaCaso {
    record Entrada(
            String emailCoordenador,
            Disciplina disciplina,
            Turno turno,
            String matriculaProfessorTg,
            Integer ano,
            Integer semestre
    ) {}

    record Saida(
            Long idTurma,
            Long idCurso,
            Disciplina disciplina,
            Turno turno,
            Long idProfessorTg,
            Integer ano,
            Integer semestre
    ) {}

    Saida executar(Entrada entrada);
}