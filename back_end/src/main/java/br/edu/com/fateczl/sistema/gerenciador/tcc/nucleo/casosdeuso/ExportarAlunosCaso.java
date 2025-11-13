package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Aluno;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Turma;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.Turno;

import java.io.InputStream;
import java.util.List;

public interface ExportarAlunosCaso {
    record Entrada(
            String emailProfessorTg,
            Long idTurma,
            InputStream arquivoBruto
    ) {}

    record Saida(
            Long idTurma,
            String nomeCurso,
            Turno turno,
            Disciplina disciplina,
            Integer ano,
            Integer semestre,
            List<Aluno> alunos
    ) {}

    Saida executar(Entrada entrada);
}