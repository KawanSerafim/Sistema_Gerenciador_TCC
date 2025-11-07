package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .StatusContaUsuario;

public class ContaUsuario {
    private String email;
    private String senha;
    private StatusContaUsuario status;

    public ContaUsuario() {}

    public ContaUsuario(String email, String senha) {
        this.setEmail(email);
        this.setSenha(senha);
        this.setStatus(StatusContaUsuario.VERIFICACAO_PENDENTE);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email == null || email.isBlank()) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "Conta de Usuário: Validação falhou. O campo obrigatório "
                    + "'email' estava nulo ou vazio."
            );
        }

        if(!email.contains("@")) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_002_FORMATO_INVALIDO,
                    "Conta de Usuário: O campo 'email' possui formato inválido."
                    + " (Valor Recebido: " + email + ")"
            );
        }
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if(senha == null || senha.isBlank()) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "Conta de Usuário: Validação falhou. O campo obrigatório "
                    + "'senha' estava nulo ou vazio."
            );
        }
        this.senha = senha;
    }

    public StatusContaUsuario getStatus() {
        return status;
    }

    public void setStatus(StatusContaUsuario status) {
        if(status == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "Conta de Usuário: Validação falhou. O campo obrigatório "
                    + "'status' estava nulo ou vazio."
            );
        }
        this.status = status;
    }
}