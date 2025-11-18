package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.mapeadores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.GrupoTccModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .GrupoTcc;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GrupoTccMapeador {
    private final ProfessorMapeador orientadorMapeador;
    private final CoorientadorMapeador coorientadorMapeador;
    private final AlunoMapeador alunoMapeador;
    private final TurmaMapeador turmaMapeador;

    public GrupoTccMapeador(
            ProfessorMapeador orientadorMapeador,
            CoorientadorMapeador coorientadorMapeador,
            AlunoMapeador alunoMapeador,
            TurmaMapeador turmaMapeador
    ) {
        this.orientadorMapeador = orientadorMapeador;
        this.coorientadorMapeador = coorientadorMapeador;
        this.alunoMapeador = alunoMapeador;
        this.turmaMapeador = turmaMapeador;
    }

    public GrupoTccModelo paraModelo(GrupoTcc dominio) {
        if(dominio == null) return null;

        var grupoTccModelo = new GrupoTccModelo();
        grupoTccModelo.setId(dominio.getId());

        if(dominio.getOrientador() != null){
            grupoTccModelo.setOrientador(
                    orientadorMapeador.paraModelo(dominio.getOrientador())
            );
        }

        if(dominio.getCoorientador() != null){
            grupoTccModelo.setCoorientador(
                    coorientadorMapeador.paraModelo(dominio.getCoorientador())
            );
        }

        grupoTccModelo.setTema(dominio.getTema());
        grupoTccModelo.setTipoTcc(dominio.getTipoTcc());
        grupoTccModelo.setDisciplina(dominio.getDisciplina());

        if(dominio.getAlunos() != null) {
            grupoTccModelo.setAlunos(
                    dominio.getAlunos().stream()
                            .map(alunoMapeador::paraModelo)
                            .collect(Collectors.toList())
            );
        }

        grupoTccModelo.setTurma(
                turmaMapeador.paraModelo(dominio.getTurma())
        );

        return grupoTccModelo;
    }

    public GrupoTcc paraDominio(GrupoTccModelo modelo) {
        if(modelo == null) return null;

        var grupoTcc = new GrupoTcc();
        grupoTcc.setId(modelo.getId());

        if(modelo.getOrientador() != null){
            grupoTcc.setOrientador(
                    orientadorMapeador.paraDominio(modelo.getOrientador())
            );
        }

        if(modelo.getCoorientador() != null){
            grupoTcc.setCoorientador(
                    coorientadorMapeador.paraDominio(modelo.getCoorientador())
            );
        }

        grupoTcc.setTema(modelo.getTema());
        grupoTcc.setTipoTcc(modelo.getTipoTcc());
        grupoTcc.setDisciplina(modelo.getDisciplina());

        if(modelo.getAlunos() != null) {
            grupoTcc.setAlunos(
                    modelo.getAlunos().stream()
                            .map(alunoMapeador::paraDominio)
                            .collect(Collectors.toList())
            );
        }

        grupoTcc.setTurma(
                turmaMapeador.paraDominio(modelo.getTurma())
        );

        return grupoTcc;
    }
}