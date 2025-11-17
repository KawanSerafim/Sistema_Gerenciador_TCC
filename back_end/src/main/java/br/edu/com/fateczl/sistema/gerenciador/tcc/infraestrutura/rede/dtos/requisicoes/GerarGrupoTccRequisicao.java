package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .requisicoes;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.TipoTcc;

import java.util.List;

public record GerarGrupoTccRequisicao(
        String tema,
        TipoTcc tipoTcc,
        Disciplina disciplina,
        List<String> matriculasAluno,
        Long idTurma
) {}