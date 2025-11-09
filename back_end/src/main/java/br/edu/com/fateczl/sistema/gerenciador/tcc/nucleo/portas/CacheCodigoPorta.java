package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas;

import java.util.Optional;

public interface CacheCodigoPorta {
    String colocarCodigo(String codigo, String email);

    Optional<String> pegarEmailPeloCodigo(String codigo);

    void removerCodigo(String codigo);
}