package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia.mysql.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "administradores")
public class AdministradorModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Embedded
    private ContaUsuarioModelo contaUsuario;

    public AdministradorModelo() {}

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
        this.nome = nome;
    }

    public ContaUsuarioModelo getContaUsuario() {
        return contaUsuario;
    }

    public void setContaUsuario(ContaUsuarioModelo contaUsuario) {
        this.contaUsuario = contaUsuario;
    }
}