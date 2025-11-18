package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.TipoTcc;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grupos_tcc")
public class GrupoTccModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_orientador")
    private ProfessorModelo orientador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_coorientador")
    private CoorientadorModelo coorientador;

    @Column(nullable = false, length = 100)
    private String tema;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTcc tipoTcc;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Disciplina disciplina;

    @ManyToMany
    @JoinTable(
            name = "grupo_tcc_alunos",
            joinColumns = @JoinColumn(name = "id_grupo_tcc"),
            inverseJoinColumns = @JoinColumn(name = "id_aluno")
    )
    private List<AlunoModelo> alunos = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_turma")
    private TurmaModelo turma;

    public GrupoTccModelo() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProfessorModelo getOrientador() {
        return orientador;
    }

    public void setOrientador(ProfessorModelo orientador) {
        this.orientador = orientador;
    }

    public CoorientadorModelo getCoorientador() {
        return coorientador;
    }

    public void setCoorientador(CoorientadorModelo coorientador) {
        this.coorientador = coorientador;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public TipoTcc getTipoTcc() {
        return tipoTcc;
    }

    public void setTipoTcc(TipoTcc tipoTcc) {
        this.tipoTcc = tipoTcc;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public List<AlunoModelo> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<AlunoModelo> alunos) {
        this.alunos = alunos;
    }

    public TurmaModelo getTurma() {
        return turma;
    }

    public void setTurma(TurmaModelo turma) {
        this.turma = turma;
    }
}