package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.Turno;

import java.util.List;

public class ParametrosCurso {
    private List<Turno> turnos;
    private List<Disciplina> disciplinas;

    public ParametrosCurso() {}

    public ParametrosCurso(List<Turno> turnos, List<Disciplina> disciplinas) {
        this.setTurnos(turnos);
        this.setDisciplinas(disciplinas);
    }

    public boolean validarDisciplina(Disciplina disciplina) {
        return this.getDisciplinas().contains(disciplina);
    }

    public boolean validarTurno(Turno turno) {
        return getTurnos().contains(turno);
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        if(turnos == null || turnos.isEmpty()) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "ParametrosCurso: Validação falhou. O campo obrigatório "
                    + "'turnos' estava nulo ou vazio."
            );
        }
        this.turnos = turnos;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        if(disciplinas == null || disciplinas.isEmpty()) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "ParametrosCurso: Validação falhou. O campo obrigatório "
                    + "'disciplinas' estava nulo ou vazio."
            );
        }
        this.disciplinas = disciplinas;
    }
}