package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso;

public interface LoginCaso {
    record Entrada(String email, String senha, String IpRequisicao) {}

    record Saida(String token, String tipoToken) {}

    Saida executar(Entrada entrada);
}