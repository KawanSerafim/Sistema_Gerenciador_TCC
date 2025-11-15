package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.TipoTcc;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes.CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes.ExcecaoDominio;

public class AjusteTipoTcc {
    private TipoTcc tipoTcc;
    private Integer maxAlunosGrupo;

    public AjusteTipoTcc() {}

    public AjusteTipoTcc(TipoTcc tipoTcc, Integer maxAlunosGrupo) {
        this.setTipoTcc(tipoTcc);
        this.setMaxAlunosGrupo(maxAlunosGrupo);
    }

    public TipoTcc getTipoTcc() {
        return tipoTcc;
    }

    public void setTipoTcc(TipoTcc tipoTcc) {
        if(tipoTcc == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "[AjusteTipoTcc]: Validação falhou. O campo obrigatório "
                    + "'[Tipo de TCC]' estava nulo ou vazio."
            );
        }
        this.tipoTcc = tipoTcc;
    }

    public Integer getMaxAlunosGrupo() {
        return maxAlunosGrupo;
    }

    public void setMaxAlunosGrupo(Integer maxAlunosGrupo) {
        if(maxAlunosGrupo == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "[AjusteTipoTcc]: Validação falhou. O campo obrigatório "
                    + "'[Máximo de Alunos por Grupo]' estava nulo ou vazio."
            );
        }
        this.maxAlunosGrupo = maxAlunosGrupo;
    }
}