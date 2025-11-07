package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos.respostas;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .CargoProfessor;

public record ProfessorResposta(
        Long id,
        String nome,
        String matricula,
        CargoProfessor cargo
) {}