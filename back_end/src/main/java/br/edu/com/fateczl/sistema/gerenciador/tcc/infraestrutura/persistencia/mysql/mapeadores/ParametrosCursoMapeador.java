package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.mapeadores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.ParametrosCursoModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .ParametrosCurso;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ParametrosCursoMapeador {
    private final AjusteTipoTccMapeador ajusteMapeador;

    public ParametrosCursoMapeador(
            AjusteTipoTccMapeador ajusteMapeador
    ) {
        this.ajusteMapeador = ajusteMapeador;
    }

    public ParametrosCursoModelo paraModelo(ParametrosCurso dominio) {
        if(dominio == null) return null;

        var parametrosCursoModelo = new ParametrosCursoModelo();
        parametrosCursoModelo.setDisciplinas(dominio.getDisciplinas());
        parametrosCursoModelo.setTurnos(dominio.getTurnos());

        if(dominio.getAjustesTipoTcc() != null){
            parametrosCursoModelo.setAjustesTipoTcc(
                    dominio.getAjustesTipoTcc().stream()
                            .map(ajusteMapeador::paraModelo)
                            .collect(Collectors.toList())
            );
        }
        return parametrosCursoModelo;
    }

    public ParametrosCurso paraDominio(ParametrosCursoModelo modelo) {
        if(modelo == null) return null;

        var parametrosCurso = new ParametrosCurso();
        parametrosCurso.setDisciplinas(modelo.getDisciplinas());
        parametrosCurso.setTurnos(modelo.getTurnos());

        if(modelo.getAjustesTipoTcc() != null){
            parametrosCurso.setAjustesTipoTcc(
                    modelo.getAjustesTipoTcc().stream()
                            .map(ajusteMapeador::paraDominio)
                            .collect(Collectors.toList())
            );
        }
        return parametrosCurso;
    }
}