package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .CargoProfessor;
import jakarta.persistence.*;

@Entity
public class CoorientadorModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 100)
    private String origem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CargoProfessor cargoProfessor;

    public CoorientadorModelo() {}

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

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public CargoProfessor getCargoProfessor() {
        return cargoProfessor;
    }

    public void setCargoProfessor(CargoProfessor cargoProfessor) {
        this.cargoProfessor = cargoProfessor;
    }
}