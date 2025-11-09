package br.edu.com.fateczl.sistema.gerenciador.tcc.aplicacao.servicos;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso.LoginCaso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas
        .GeradorTokenPorta;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas
        .ProvedorAutenticacaoPorta;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.excecoes
        .ExcecaoContaInvalida;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.excecoes
        .ExcecaoCredencialInvalida;
import org.springframework.stereotype.Service;

@Service
public class LoginServico implements LoginCaso {
    private final ProvedorAutenticacaoPorta provedorAutenticacao;
    private final GeradorTokenPorta geradorToken;

    public LoginServico(
            ProvedorAutenticacaoPorta provedorAutenticacao,
            GeradorTokenPorta geradorToken
    ) {
        this.provedorAutenticacao = provedorAutenticacao;
        this.geradorToken = geradorToken;
    }

    @Override
    public Saida executar(Entrada entrada) {
        verificarCampoObrigatorio(entrada.email());
        verificarCampoObrigatorio(entrada.senha());

        try {
            provedorAutenticacao.autenticar(entrada.email(), entrada.senha());

            String token = geradorToken.gerarToken(entrada.email());
            return new Saida(token, "Bearer");
        } catch(ExcecaoCredencialInvalida ex) {
            throw new ExcecaoDominio(
                    CodigoErro.AU_002_CREDENCIAS_INVALIDAS,
                    "Autenticação: Falha na tentativa de login para o usuário "
                    + " [" + entrada.email() + "]. (Motivo: Credenciais "
                    + "inválidas, IP: [" + entrada.ipRequisicao() + "])",
                    ex
            );
        } catch(ExcecaoContaInvalida ex) {
            throw new ExcecaoDominio(
                    CodigoErro.AU_003_CONTA_INVALIDA,
                    "Autenticação: Falha na tentativa de login para o usuário "
                    + " [" + entrada.email() + "]. (Motivo: Conta de estádo "
                    + "inválido, IP: [" + entrada.ipRequisicao() + "])",
                    ex
            );
        }
    }

    private void verificarCampoObrigatorio(String campo) {
        if(campo == null || campo.isBlank()) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "Validação falhou. O campo obrigatório 'email' e/ou "
                    + "'senha' estava nulo ou vazio."
            );
        }
    }
}