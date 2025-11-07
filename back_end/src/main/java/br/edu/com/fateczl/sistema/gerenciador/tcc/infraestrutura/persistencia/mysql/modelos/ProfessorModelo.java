package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .CargoProfessor;
import jakarta.persistence.*;

@Entity
@Table(name = "professores")
public class ProfessorModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String matricula;

    @Embedded
    private ContaUsuarioModelo contaUsuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CargoProfessor cargo;

    public ProfessorModelo() {}

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

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public ContaUsuarioModelo getContaUsuario() {
        return contaUsuario;
    }

    public void setContaUsuario(ContaUsuarioModelo contaUsuario) {
        this.contaUsuario = contaUsuario;
    }

    public CargoProfessor getCargo() {
        return cargo;
    }

    public void setCargo(CargoProfessor cargo) {
        this.cargo = cargo;
    }
}