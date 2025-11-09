package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.excecoes;

public class ExcecaoContaInvalida extends ExcecaoErroAutenticacaoPorta {
    public ExcecaoContaInvalida(Throwable causa) {
        super("Conta bloqueada ou desabilitada", causa);
    }
}