package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.Turno;

import java.util.ArrayList;
import java.util.List;

public class ParametrosCurso {
    private List<Turno> turnos;
    private List<Disciplina> disciplinas;
    private List<AjusteTipoTcc> ajustesTipoTcc = new ArrayList<>();

    public ParametrosCurso() {}

    public ParametrosCurso(
            List<Turno> turnos,
            List<Disciplina> disciplinas,
            List<AjusteTipoTcc> ajustesTipoTcc
    ) {
        this.setTurnos(turnos);
        this.setDisciplinas(disciplinas);
        this.setAjustesTipoTcc(ajustesTipoTcc);
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
                    "[ParametrosCurso]: Validação falhou. O campo obrigatório "
                    + "'[Turnos]' estava nulo ou vazio."
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
                    "[ParametrosCurso]: Validação falhou. O campo obrigatório "
                    + "'[Disciplinas]' estava nulo ou vazio."
            );
        }
        this.disciplinas = disciplinas;
    }

    public List<AjusteTipoTcc> getAjustesTipoTcc() {
        return ajustesTipoTcc;
    }

    public void setAjustesTipoTcc(List<AjusteTipoTcc> ajustesTipoTcc) {
        if(ajustesTipoTcc == null || ajustesTipoTcc.isEmpty()) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "[ParametrosCurso]: Validação falhou. O campo obrigatório "
                    + "'[Ajustes de Tipos de TCC]' estava nulo ou vazio."
            );
        }
        this.ajustesTipoTcc = ajustesTipoTcc;
    }
}