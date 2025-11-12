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
            Turma turma,
            List<Aluno> alunos
    ) {
        public Long idTurma() {
            return this.turma.getId();
        }

        public String nomeCurso() {
            return this.turma.getNomeCurso();
        }

        public Turno turno() {
            return this.turma.getTurno();
        }

        public Disciplina disciplina() {
            return this.turma.getDisciplina();
        }

        public Integer ano() {
            return this.turma.getAno();
        }

        public Integer semestre() {
            return this.turma.getSemestre();
        }
    }

    Saida executar(Entrada entrada);
}