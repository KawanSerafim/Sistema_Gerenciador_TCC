package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.excecoes;

public abstract class ExcecaoErroAutenticacaoPorta extends Exception {
    public ExcecaoErroAutenticacaoPorta(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}