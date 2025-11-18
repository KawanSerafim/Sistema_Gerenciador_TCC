package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .CargoProfessor;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;

public class Coorientador {
    private Long id;
    private String nome;
    private String origem;
    private CargoProfessor cargoProfessor;

    public Coorientador() {}

    public Coorientador(String nome, String origem) {
        this.setNome(nome);
        this.setOrigem(origem);
        this.setCargoProfessor(CargoProfessor.ORIENTADOR);
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
                    "[Coorientador] (ID: [" + this.getId() + "]): Validação "
                    + "falhou. O campo obrigatório '[Nome]' estava nulo ou "
                    + "vazio."
            );
        }
        this.nome = nome;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        if(origem == null || origem.isBlank()) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "[Coorientador] (ID: [" + this.getId() + "]): Validação "
                    + "falhou. O campo obrigatório '[Origem]' estava nulo ou "
                    + "vazio."
            );
        }
        this.origem = origem;
    }

    public CargoProfessor getCargoProfessor() {
        return cargoProfessor;
    }

    public void setCargoProfessor(CargoProfessor cargoProfessor) {
        if(cargoProfessor == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "[Coorientador] (ID: [" + this.getId() + "]): Validação "
                    + "falhou. O campo obrigatório '[Cargo de Professor]' "
                    + "estava nulo ou vazio."
            );
        }
        this.cargoProfessor = cargoProfessor;
    }
}