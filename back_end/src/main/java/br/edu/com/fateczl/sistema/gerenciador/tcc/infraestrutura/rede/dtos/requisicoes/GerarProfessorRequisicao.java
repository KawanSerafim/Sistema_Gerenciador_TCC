package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .requisicoes;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .CargoProfessor;

public record GerarProfessorRequisicao(
        String nome,
        String matricula,
        String email,
        String senha,
        CargoProfessor cargo
) {}