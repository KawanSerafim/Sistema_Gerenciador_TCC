package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede
        .dtos.respostas;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;

public record ErroResposta(CodigoErro codigo, String mensagem) {}