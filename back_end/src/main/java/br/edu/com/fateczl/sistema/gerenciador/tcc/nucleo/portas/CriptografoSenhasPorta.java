package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas;

public interface CriptografoSenhasPorta {
    String criptografar(String senha);

    String comparar(String senhaRecebida, String senhaCadastrada);
}