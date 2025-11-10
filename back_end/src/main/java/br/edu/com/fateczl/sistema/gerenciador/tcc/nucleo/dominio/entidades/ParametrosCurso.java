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
    private Integer maxAlunosGrupo;

    public ParametrosCurso() {}

    public ParametrosCurso(
            List<Turno> turnos,
            List<Disciplina> disciplinas,
            Integer maxAlunosGrupo
    ) {
        this.setTurnos(turnos);
        this.setDisciplinas(disciplinas);
        this.setMaxAlunosGrupo(maxAlunosGrupo);
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

    public Integer getMaxAlunosGrupo() {
        return maxAlunosGrupo;
    }

    public void setMaxAlunosGrupo(Integer maxAlunosGrupo) {
        if(maxAlunosGrupo == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "ParametrosCurso: Validação falhou. O campo obrigatório "
                    + "'máximo de alunos por grupo' estava nulo ou vazio."
            );
        }

        if(maxAlunosGrupo < 1) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_005_PADRAO_INVALIDO,
                    "Parâmetros de Curso: O campo 'máximo de alunos por grupo' "
                    + "não segue o padrão esperado. (Valor: '[" + maxAlunosGrupo
                    + "]', Padrão: '[Valor >= 1]')"
            );
        }
        this.maxAlunosGrupo = maxAlunosGrupo;
    }
}