package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso;

public interface FinalizarCadastroAlunoCaso {
    record Entrada(String matricula, String email, String senha) {}

    void executar(Entrada entrada);
}