package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.configuracoes
        .seguranca.criptografia;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.CriptografoSenhasPorta;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CriptografoSenhasAdaptador implements CriptografoSenhasPorta {
    private final PasswordEncoder encoder;

    public CriptografoSenhasAdaptador(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public String criptografar(String senha) {
        return this.encoder.encode(senha);
    }

    @Override
    public boolean comparar(String senhaRecebida, String senhaCadastrada) {
        return this.encoder.matches(senhaRecebida, senhaCadastrada);
    }
}