package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.mapeadores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.AjusteTipoTccModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .AjusteTipoTcc;
import org.springframework.stereotype.Component;

@Component
public class AjusteTipoTccMapeador {
    public AjusteTipoTccModelo paraModelo(AjusteTipoTcc dominio) {
        if(dominio == null) return null;

        var ajusteTipoTccModelo = new AjusteTipoTccModelo();
        ajusteTipoTccModelo.setTipoTcc(dominio.getTipoTcc());
        ajusteTipoTccModelo.setMaxAlunosGrupo(dominio.getMaxAlunosGrupo());

        return ajusteTipoTccModelo;
    }

    public AjusteTipoTcc paraDominio(AjusteTipoTccModelo modelo) {
        if(modelo == null) return null;

        var ajusteTipoTcc = new AjusteTipoTcc();
        ajusteTipoTcc.setTipoTcc(modelo.getTipoTcc());
        ajusteTipoTcc.setMaxAlunosGrupo(modelo.getMaxAlunosGrupo());

        return ajusteTipoTcc;
    }
}