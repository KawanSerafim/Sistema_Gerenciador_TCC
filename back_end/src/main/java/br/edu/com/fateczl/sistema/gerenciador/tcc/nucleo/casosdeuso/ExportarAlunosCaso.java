package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Aluno;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Turma;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.Turno;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas
        .LeitorArquivoDadosAlunos;

import java.util.List;

public interface ExportarAlunosCaso {
    record Entrada(
            Long idTurma,
            LeitorArquivoDadosAlunos.DadosArquivo dadosArquivo
    ) {
        public List<LeitorArquivoDadosAlunos.DadosAlunos> alunos() {
            return this.dadosArquivo.alunos();
        }
    }

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