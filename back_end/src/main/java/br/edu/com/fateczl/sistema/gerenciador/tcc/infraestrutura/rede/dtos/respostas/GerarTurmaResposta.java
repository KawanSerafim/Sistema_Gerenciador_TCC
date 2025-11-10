package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .respostas;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Turno;

public record GerarTurmaResposta(
        Long idTurma,
        Long idCurso,
        Disciplina disciplina,
        Turno turno,
        Long idProfessorTg,
        Integer ano,
        Integer semestre
) {}