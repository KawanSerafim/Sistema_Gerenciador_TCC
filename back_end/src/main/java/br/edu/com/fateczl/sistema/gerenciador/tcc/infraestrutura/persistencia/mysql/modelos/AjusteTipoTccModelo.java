package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.TipoTcc;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class AjusteTipoTccModelo {
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_tcc", nullable = false)
    private TipoTcc tipoTcc;

    @Column(name = "max_alunos_grupo", nullable = false)
    private Integer maxAlunosGrupo;

    public AjusteTipoTccModelo() {}

    public TipoTcc getTipoTcc() {
        return tipoTcc;
    }

    public void setTipoTcc(TipoTcc tipoTcc) {
        this.tipoTcc = tipoTcc;
    }

    public Integer getMaxAlunosGrupo() {
        return maxAlunosGrupo;
    }

    public void setMaxAlunosGrupo(Integer maxAlunosGrupo) {
        this.maxAlunosGrupo = maxAlunosGrupo;
    }
}