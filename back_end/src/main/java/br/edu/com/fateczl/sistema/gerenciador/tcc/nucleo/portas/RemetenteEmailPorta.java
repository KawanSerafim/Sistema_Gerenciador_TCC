package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas;

public interface RemetenteEmailPorta {
    void enviarEmail(String para, String assunto, String corpo);
}