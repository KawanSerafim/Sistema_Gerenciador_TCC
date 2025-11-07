package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .respostas;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.excecoes
        .CodigoErro;

public record ErroInfraResposta(CodigoErro codigo, String mensagem) {}
