package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.excecoes;

public class ExcecaoCredencialInvalida extends ExcecaoErroAutenticacaoPorta {
    public ExcecaoCredencialInvalida(Throwable causa) {
        super("Credenciais Inválidas", causa);
    }
}