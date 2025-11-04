package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia.mysql.modelos;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .StatusContaUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class ContaUsuarioModelo {
    @Column(unique = true)
    private String email;

    @Column
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private StatusContaUsuario status;

    public ContaUsuarioModelo() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public StatusContaUsuario getStatus() {
        return status;
    }

    public void setStatus(StatusContaUsuario status) {
        this.status = status;
    }
}