package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "cursos")
public class CursoModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private ParametrosCursoModelo parametros;

    @OneToOne
    @JoinColumn(name = "id_coordenador", nullable = false)
    private ProfessorModelo coordenador;

    public CursoModelo() {}

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

    public ParametrosCursoModelo getParametros() {
        return parametros;
    }

    public void setParametros(ParametrosCursoModelo parametros) {
        this.parametros = parametros;
    }

    public ProfessorModelo getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(ProfessorModelo coordenador) {
        this.coordenador = coordenador;
    }
}