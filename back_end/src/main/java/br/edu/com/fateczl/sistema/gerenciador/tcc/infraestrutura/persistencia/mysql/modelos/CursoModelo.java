package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "cursos")
public class CursoModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private ParametrosCursoModelo parametros;

    @OneToOne
    @JoinColumn(name = "id_coordenador_curso", nullable = false)
    private ProfessorModelo coordenadorCurso;

    public CursoModelo() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public ProfessorModelo getCoordenadorCurso() {
        return coordenadorCurso;
    }

    public void setCoordenadorCurso(ProfessorModelo coordenadorCurso) {
        this.coordenadorCurso = coordenadorCurso;
    }
}