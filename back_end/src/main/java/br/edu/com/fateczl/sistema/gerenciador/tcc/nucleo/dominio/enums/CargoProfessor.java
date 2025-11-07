package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums;

public enum CargoProfessor {
    ORIENTADOR,
    PROFESSOR_TG,
    COORDENADOR_CURSO;

    public boolean podeSerOrientador() {
        return true;
    }

    public boolean podeSerProfessorTg() {
        return this == PROFESSOR_TG || this == COORDENADOR_CURSO;
    }

    public boolean podeSerCoordenadorCurso() {
        return this == COORDENADOR_CURSO;
    }
}