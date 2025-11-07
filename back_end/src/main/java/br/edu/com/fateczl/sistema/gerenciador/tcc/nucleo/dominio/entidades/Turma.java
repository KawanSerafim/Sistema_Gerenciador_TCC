package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.Turno;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;

import java.time.Year;

public class Turma {
    private Long id;
    private Curso curso;
    private Disciplina disciplina;
    private Turno turno;
    private Professor professorTg;
    private Integer ano;
    private Integer semestre;

    public Turma() {}

    public Turma(
            Curso curso,
            Disciplina disciplina,
            Turno turno,
            Professor professorTg,
            Integer ano,
            Integer semestre
    ) {
        this.setCurso(curso);
        this.setDisciplina(disciplina);
        this.setTurno(turno);
        this.setProfessorTg(professorTg);
        this.setAno(ano);
        this.setSemestre(semestre);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        if(curso == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "Turma (ID: " + getId() + "): Validação falhou. O campo " +
                    "obrigatório 'curso' estava nulo ou vazio."
            );
        }
        this.curso = curso;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        if(disciplina == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "Turma (ID: " + getId() + "): Validação falhou. O campo " +
                    "obrigatório 'disciplina' estava nulo ou vazio."
            );
        }

        if(this.getCurso() == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_003_ASSOCIACAO_OBRIGATORIA,
                    "Turma (ID: " + getId() + "): Associação obrigatória "
                    + "ausente. Deve estar associado a um 'curso'."
            );
        }

        if(!getCurso().validarDisciplina(disciplina)) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_007_CAMPO_NAO_SUPORTADO,
                    "Turma (ID: " + getId() + "): O campo 'disciplina' não é "
                    + "suportado [O curso '" + this.getCurso().getNome() + "' "
                    + "não possui esta disciplina]."
            );
        }
        this.disciplina = disciplina;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        if(turno == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "Turma (ID: " + getId() + "): Validação falhou. O campo " +
                    "obrigatório 'turno' estava nulo ou vazio."
            );
        }

        if(this.getCurso() == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_003_ASSOCIACAO_OBRIGATORIA,
                    "Turma (ID: " + getId() + "): Associação obrigatória "
                    + "ausente. Deve estar associado a um 'curso'."
            );
        }

        if(!getCurso().validarTurno(turno)) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_007_CAMPO_NAO_SUPORTADO,
                    "Turma (ID: " + getId() + "): O campo 'turno' não é "
                    + "suportado [O curso '" + this.getCurso().getNome() + "' "
                    + "não possui este turno]."
            );
        }
        this.turno = turno;
    }

    public Professor getProfessorTg() {
        return professorTg;
    }

    public void setProfessorTg(Professor professorTg) {
        if(professorTg == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "Turma (ID: " + getId() + "): Validação falhou. O campo " +
                    "obrigatório 'professor de tg' estava nulo ou vazio."
            );
        }
        this.professorTg = professorTg;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        if(ano == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "Turma (ID: " + getId() + "): Validação falhou. O campo " +
                    "obrigatório 'ano' estava nulo ou vazio."
            );
        }

        if(ano < Year.now().getValue()) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_004_DATA_INVALIDA,
                    "Turma (ID: " + getId() + "): Validação de data falhou p/ "
                    + "o campo ano. (Valor: " + ano + ", Condição: >= "
                    + Year.now().getValue() + ")"
            );
        }
        this.ano = ano;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        if(semestre == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "Turma (ID: " + getId() + "): Validação falhou. O campo " +
                    "obrigatório 'semestre' estava nulo ou vazio."
            );
        }

        if(semestre != 1 && semestre != 2) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_004_DATA_INVALIDA,
                    "Turma (ID: " + getId() + "): Validação de data falhou p/ "
                    + "o campo semestre. (Valor: " + semestre + ", Condição: "
                    + " 1 ou 2)"
            );
        }
        this.semestre = semestre;
    }
}