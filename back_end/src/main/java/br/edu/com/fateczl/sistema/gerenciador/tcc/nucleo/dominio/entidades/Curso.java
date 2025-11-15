package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.Turno;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;

import java.util.List;

public class Curso {
    private Long id;
    private String nome;
    private ParametrosCurso parametros;
    private Professor coordenador;

    public Curso() {}

    public Curso(
            String nome,
            ParametrosCurso parametros,
            Professor coordenador
    ) {
        this.setNome(nome);
        this.setParametros(parametros);
        this.setCoordenador(coordenador);
    }

    public boolean validarDisciplina(Disciplina disciplina) {
        return this.getParametros().validarDisciplina(disciplina);
    }

    public boolean validarTurno(Turno turno) {
        return this.getParametros().validarTurno(turno);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(nome == null || nome.isBlank()) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "Curso (ID: " + getId() + "): Validação falhou. O campo"
                    + " obrigatório 'nome' estava nulo ou vazio."
            );
        }
        this.nome = nome;
    }

    public ParametrosCurso getParametros() {
        return parametros;
    }

    public void setParametros(ParametrosCurso parametros) {
        if(parametros == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "Curso (ID: " + getId() + "): Validação falhou. O campo"
                    + " obrigatório 'parâmetros' estava nulo ou vazio."
            );
        }
        this.parametros = parametros;
    }

    public Professor getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Professor coordenador) {
        if(coordenador == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "Curso (ID: " + getId() + "): Validação falhou. O campo"
                    + " obrigatório 'coordenador de curso' estava nulo ou "
                    + "vazio."
            );
        }
        this.coordenador = coordenador;
    }

    public List<Turno> getTurnos() {
        return this.getParametros().getTurnos();
    }

    public List<Disciplina> getDisciplinas() {
        return this.getParametros().getDisciplinas();
    }

    public List<AjusteTipoTcc> getAjustesTcc() {
        return this.getParametros().getAjustesTipoTcc();
    }

    public Long getIdCoordenador() {
        return coordenador.getId();
    }
}