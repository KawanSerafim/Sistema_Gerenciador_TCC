package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.StatusContaUsuario;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes.CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes.ExcecaoDominio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContaUsuarioTest {
    @Test
    void deveConstruirContaUsuarioComDadosValidos() {
        String email = "email@teste.com";
        String senha = "senha de teste";

        var contaUsuario = new ContaUsuario(email, senha);

        assertNotNull(contaUsuario);
        assertEquals(email, contaUsuario.getEmail());
        assertEquals(senha, contaUsuario.getSenha());
        assertEquals(
                StatusContaUsuario.VERIFICACAO_PENDENTE,
                contaUsuario.getStatus()
        );
    }

    @Test
    void emDefinirEmailDeveLancarExcecaoDominioQuandoNomeENulo() {
        var contaUsuario = new ContaUsuario();

        String mensagem = "Conta de Usuário: Validação falhou. O campo "
                + "obrigatório 'email' estava nulo ou vazio.";

        var excecao = assertThrows(ExcecaoDominio.class, () ->
                contaUsuario.setEmail(null)
        );

        assertEquals(
                CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                excecao.pegarCodigoErro()
        );
        assertEquals(mensagem, excecao.getMessage());
    }

    @Test
    void emDefinirEmailDeveLancarExcecaoDominioQuandoNomeEVazio() {
        var contaUsuario = new ContaUsuario();

        String mensagem = "Conta de Usuário: Validação falhou. O campo "
                + "obrigatório 'email' estava nulo ou vazio.";

        var excecao = assertThrows(ExcecaoDominio.class, () ->
                contaUsuario.setEmail("")
        );

        assertEquals(
                CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                excecao.pegarCodigoErro()
        );
        assertEquals(mensagem, excecao.getMessage());
    }

    @Test
    void emDefinirEmailDeveLancarExcecaoDominioQuandoFormatoEInvalido() {
        var contaUsuario = new ContaUsuario();
        String email = "email.invalido.com";

        String mensagem = "Conta de Usuário: O campo 'email' possui formato "
                + "inválido. (Valor Recebido: " + email + ")";

        var excecao = assertThrows(ExcecaoDominio.class, () ->
                contaUsuario.setEmail(email)
        );

        assertEquals(
                CodigoErro.VD_002_FORMATO_INVALIDO,
                excecao.pegarCodigoErro()
        );
        assertEquals(mensagem, excecao.getMessage());
    }

    @Test
    void emDefinirSenhaDeveLancarExcecaoDominioQuandoSenhaENulo() {
        var contaUsuario = new ContaUsuario();

        String mensagem = "Conta de Usuário: Validação falhou. O campo "
                + "obrigatório 'senha' estava nulo ou vazio.";

        var excecao = assertThrows(ExcecaoDominio.class, () ->
                contaUsuario.setSenha(null)
        );

        assertEquals(
                CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                excecao.pegarCodigoErro()
        );
        assertEquals(mensagem, excecao.getMessage());
    }

    @Test
    void emDefinirSenhaDeveLancarExcecaoDominioQuandoSenhaEVazio() {
        var contaUsuario = new ContaUsuario();

        String mensagem = "Conta de Usuário: Validação falhou. O campo "
                + "obrigatório 'senha' estava nulo ou vazio.";

        var excecao = assertThrows(ExcecaoDominio.class, () ->
                contaUsuario.setSenha("")
        );

        assertEquals(
                CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                excecao.pegarCodigoErro()
        );
        assertEquals(mensagem, excecao.getMessage());
    }

    @Test
    void emDefinirStatusDeveLancarExcecaoDominioQuandoSenhaENulo() {
        var contaUsuario = new ContaUsuario();

        String mensagem = "Conta de Usuário: Validação falhou. O campo "
                + "obrigatório 'status' estava nulo ou vazio.";

        var excecao = assertThrows(ExcecaoDominio.class, () ->
                contaUsuario.setStatus(null)
        );

        assertEquals(
                CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                excecao.pegarCodigoErro()
        );
        assertEquals(mensagem, excecao.getMessage());
    }
}