package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .TipoTcc;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;

import java.util.Objects;

public class AjusteTipoTcc {
    private TipoTcc tipoTcc;
    private Integer maxAlunosGrupo;

    public AjusteTipoTcc() {}

    public AjusteTipoTcc(TipoTcc tipoTcc, Integer maxAlunosGrupo) {
        this.setTipoTcc(tipoTcc);
        this.setMaxAlunosGrupo(maxAlunosGrupo);
    }

    public boolean validarQtdAlunosGrupo(Integer quantidade) {
        if(quantidade == null) return false;

        return quantidade > 0 && quantidade <= maxAlunosGrupo;
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

        if(maxAlunosGrupo < 1) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_005_PADRAO_INVALIDO,
                    "[AjusteTipoTcc]: O campo 'Máximo de Alunos por Grupo' "
                    + "não segue o padrão esperado. (Valor: '[" + maxAlunosGrupo
                    + "]', Padrão: '[Valor >= 1]')"
            );
        }
        this.maxAlunosGrupo = maxAlunosGrupo;
    }
}