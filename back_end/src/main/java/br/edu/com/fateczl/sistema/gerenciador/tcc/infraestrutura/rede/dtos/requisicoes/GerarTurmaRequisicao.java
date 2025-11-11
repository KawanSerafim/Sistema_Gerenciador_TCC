package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .requisicoes;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Turno;

public record GerarTurmaRequisicao(
        Disciplina disciplina,
        Turno turno,
        String matriculaProfessorTg,
        Integer ano,
        Integer semestre
) {}