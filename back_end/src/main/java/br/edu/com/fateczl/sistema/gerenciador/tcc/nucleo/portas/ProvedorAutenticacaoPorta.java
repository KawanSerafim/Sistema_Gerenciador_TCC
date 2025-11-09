package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas;

public interface ProvedorAutenticacaoPorta {
    void autenticar(String email, String senha);
}