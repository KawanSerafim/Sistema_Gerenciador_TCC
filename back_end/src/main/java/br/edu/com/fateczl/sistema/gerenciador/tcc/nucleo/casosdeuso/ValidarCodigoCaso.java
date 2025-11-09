package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso;

public interface ValidarCodigoCaso {
    record Entrada(String codigo) {}

    void executar(Entrada entrada);
}