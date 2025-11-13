package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .respostas;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .StatusAluno;

public record AlunoResposta(
        Long id,
        String nome,
        String matricula,
        StatusAluno statusAluno
) {}