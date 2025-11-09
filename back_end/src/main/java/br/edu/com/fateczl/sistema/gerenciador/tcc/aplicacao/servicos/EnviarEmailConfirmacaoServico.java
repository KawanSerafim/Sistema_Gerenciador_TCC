package br.edu.com.fateczl.sistema.gerenciador.tcc.aplicacao.servicos;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso
        .EnviarEmailConfirmacaoCaso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .ContaUsuario;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas
        .CacheCodigoPorta;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas
        .RemetenteEmailPorta;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EnviarEmailConfirmacaoServico implements EnviarEmailConfirmacaoCaso {
    private final CacheCodigoPorta cacheCodigo;
    private final RemetenteEmailPorta remetenteEmail;

    public EnviarEmailConfirmacaoServico(
            CacheCodigoPorta cacheCodigo,
            RemetenteEmailPorta remetenteEmail
    ) {
        this.cacheCodigo = cacheCodigo;
        this.remetenteEmail = remetenteEmail;
    }

    @Override
    public void executar(Entrada entrada) {
        ContaUsuario.validarFormatoEmail(entrada.email());

        String codigo = gerarCodigo();
        String assunto = "Confirme seu Email";
        String corpo = gerarCorpoEmail(codigo);

        cacheCodigo.colocarCodigo(codigo, entrada.email());
        remetenteEmail.enviarEmail(entrada.email(), assunto, corpo);
    }

    private String gerarCodigo() {
        int tamanho = 6;
        Random random = new Random();
        StringBuilder codigo = new StringBuilder(tamanho);
        String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvwxyz0123456789";

        for(int i = 0; i < tamanho; i++){
            codigo.append(
                    CARACTERES.charAt(random.nextInt(CARACTERES.length()))
            );
        }

        return codigo.toString();
    }

    private String gerarCorpoEmail(String codigo) {
        return
            "Olá e seja bem-vindo!\n\n"
            + "Você está a um passo de concluir o seu cadastro, e para isso "
            + "é preciso inserir o código abaixo no formulário.\n\n"
            + "Código: " + codigo + "\n\n"
            + "Atenção: Este código de confirmação é válido por apenas "
            + "15 minutos. Se ele expirar, será preciso solicitar um novo "
            + "código.";
    }
}