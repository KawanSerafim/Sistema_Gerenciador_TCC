package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso;

public interface EnviarEmailCaso {
    record Entrada(String email) {}

    void executar(Entrada entrada);
}