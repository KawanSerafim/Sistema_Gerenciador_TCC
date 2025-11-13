package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.excecoes
        .ExcecaoContaInvalida;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.excecoes
        .ExcecaoCredencialInvalida;

public interface ProvedorAutenticacaoPorta {
    void autenticar(String email, String senha)
            throws ExcecaoCredencialInvalida, ExcecaoContaInvalida;
}