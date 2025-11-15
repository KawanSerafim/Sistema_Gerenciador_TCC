package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.Turno;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class ParametrosCursoModelo {
    @ElementCollection(targetClass = Turno.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "turnos",
            joinColumns = @JoinColumn(name = "id_curso")
    )
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private List<Turno> turnos;

    @ElementCollection(targetClass = Disciplina.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "disciplinas",
            joinColumns = @JoinColumn(name = "id_curso")
    )
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private List<Disciplina> disciplinas;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "curso_ajustes_tcc",
            joinColumns = @JoinColumn(name = "id_curso")
    )
    private List<AjusteTipoTccModelo> ajustesTipoTcc = new ArrayList<>();

    public ParametrosCursoModelo() {}

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public List<AjusteTipoTccModelo> getAjustesTipoTcc() {
        return ajustesTipoTcc;
    }

    public void setAjustesTipoTcc(List<AjusteTipoTccModelo> ajustesTipoTcc) {
        this.ajustesTipoTcc = ajustesTipoTcc;
    }
}