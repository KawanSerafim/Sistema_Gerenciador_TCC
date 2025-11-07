package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .StatusAluno;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alunos")
public class AlunoModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String matricula;

    @Embedded
    private ContaUsuarioModelo contaUsuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private StatusAluno status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "turmas_matriculadas",
            joinColumns = @JoinColumn(name = "id_aluno"),
            inverseJoinColumns = @JoinColumn(name = "id_turma")
    )
    private List<TurmaModelo> turmas = new ArrayList<>();

    public AlunoModelo() {}

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

    public StatusAluno getStatus() {
        return status;
    }

    public void setStatus(StatusAluno status) {
        this.status = status;
    }

    public List<TurmaModelo> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<TurmaModelo> turmas) {
        this.turmas = turmas;
    }
}