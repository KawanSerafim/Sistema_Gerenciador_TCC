package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .respostas;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.Turno;

import java.util.List;

public record ExportarAlunosResposta(
        Long idTurma,
        String nomeCurso,
        Turno turno,
        Disciplina disciplina,
        Integer ano,
        Integer semestre,
        List<AlunoResposta> alunos
) {}