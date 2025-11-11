package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .respostas;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Turno;

public record GerarTurmaResposta(
        Long idTurma,
        String nomeCurso,
        Disciplina disciplina,
        Turno turno,
        String nomeProfessorTg,
        Integer ano,
        Integer semestre
) {}