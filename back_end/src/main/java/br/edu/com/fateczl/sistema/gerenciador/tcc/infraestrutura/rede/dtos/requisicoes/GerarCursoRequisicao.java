package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .requisicoes;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.Turno;

import java.util.List;

public record GerarCursoRequisicao(
        String nome,
        List<Turno> turnos,
        List<Disciplina> disciplinas,
        Integer maxAlunosGrupo,
        String matriculaCoordenador
) {}