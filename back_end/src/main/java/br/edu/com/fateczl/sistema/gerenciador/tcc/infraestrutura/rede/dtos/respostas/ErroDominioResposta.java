package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede
        .dtos.respostas;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;

public record ErroDominioResposta(CodigoErro codigo, String mensagem) {}