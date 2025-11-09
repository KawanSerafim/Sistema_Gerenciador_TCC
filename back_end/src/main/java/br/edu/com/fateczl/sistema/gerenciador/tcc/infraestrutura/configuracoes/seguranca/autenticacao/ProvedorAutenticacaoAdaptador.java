package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.configuracoes
        .seguranca.autenticacao;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas
        .ProvedorAutenticacaoPorta;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.excecoes
        .ExcecaoContaInvalida;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.excecoes
        .ExcecaoCredencialInvalida;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.Component;

@Component
public class ProvedorAutenticacaoAdaptador implements ProvedorAutenticacaoPorta {

    private final AuthenticationManager authenticationManager;

    public ProvedorAutenticacaoAdaptador(
            AuthenticationManager authenticationManager
    ) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void autenticar(String email, String senha)
            throws ExcecaoCredencialInvalida, ExcecaoContaInvalida {
        try {
            var credenciais = new UsernamePasswordAuthenticationToken(
                    email,
                    senha
            );

            authenticationManager.authenticate(credenciais);
        } catch(BadCredentialsException ex) {
            throw new ExcecaoCredencialInvalida(ex);
        } catch(DisabledException | LockedException ex) {
            throw new ExcecaoContaInvalida(ex);
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}