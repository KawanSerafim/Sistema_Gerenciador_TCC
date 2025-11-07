package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.StatusContaUsuario;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;

public class Administrador {
    private Long id;
    private String nome;
    private ContaUsuario contaUsuario;

    public Administrador() {}

    public Administrador(String nome, ContaUsuario contaUsuario) {
        this.setNome(nome);
        this.setContaUsuario(contaUsuario);
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
                    "Administrador (ID: " + getId() + "): Validação falhou. O "
                    + "campo obrigatório nome estava nulo ou vazio."
            );
        }
        this.nome = nome;
    }

    public ContaUsuario getContaUsuario() {
        return contaUsuario;
    }

    public void setContaUsuario(ContaUsuario contaUsuario) {
        if(contaUsuario == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "Administrador (ID: " + getId() + "): Validação falhou. O "
                    + "campo obrigatório 'conta de usuário' estava nulo ou "
                    + "vazio."
            );
        }
        this.contaUsuario = contaUsuario;
    }

    public String getEmailContaUsuario() {
        return contaUsuario.getEmail();
    }

    public String getSenhaContaUsuario() {
        return contaUsuario.getSenha();
    }

    public StatusContaUsuario getStatusContaUsuario() {
        return contaUsuario.getStatus();
    }
}