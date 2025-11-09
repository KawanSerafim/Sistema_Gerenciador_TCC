package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.adaptadores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas
        .CacheCodigoPorta;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.Optional;

public class CacheCodigoAdaptador implements CacheCodigoPorta {
    private static final String NOME_CACHE = "codigosEmail";

    @Override
    @CachePut(value = NOME_CACHE, key = "#codigo")
    public String colocarCodigo(String codigo, String email) {
        return email;
    }

    @Override
    @Cacheable(value = NOME_CACHE, key = "#codigo")
    public Optional<String> pegarEmailPeloCodigo(String codigo) {
        return Optional.empty();
    }

    @Override
    @CacheEvict(value = NOME_CACHE, key = "#codigo")
    public void removerCodigo(String codigo) {}
}