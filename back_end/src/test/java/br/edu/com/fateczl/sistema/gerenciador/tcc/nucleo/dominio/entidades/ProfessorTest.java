package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .CargoProfessor;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes.CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes.ExcecaoDominio;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ProfessorTest {
    @Test
    void deveCadastrarProfessorComDadosValidos() {
        String nome = "Nome de teste";
        String matricula = "Matricula de Teste";
        var contaUsuario = mock(ContaUsuario.class);

        var professor = new Professor(
                nome,
                matricula,
                contaUsuario,
                CargoProfessor.ORIENTADOR
        );

        assertNotNull(professor);
        assertEquals(nome, professor.getNome());
        assertEquals(matricula, professor.getMatricula());
        assertEquals(contaUsuario, professor.getContaUsuario());
        assertEquals(CargoProfessor.ORIENTADOR, professor.getCargo());
    }

    @Test
    void emDefinirNomeDeveLancarExcecaoDominioQuandoNomeENulo() {
        var professor = new Professor();

        Long id = 1L;
        professor.setId(id);

        String mensagem = "Professor (ID: " + id + "): Validação falhou. O "
                + "campo obrigatório 'nome' estava nulo ou vazio.";

        var excecao = assertThrows(ExcecaoDominio.class, () ->
                professor.setNome(null)
        );

        assertEquals(
                CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                excecao.pegarCodigoErro()
        );
        assertEquals(mensagem, excecao.getMessage());
    }

    @Test
    void emDefinirNomeDeveLancarExcecaoDominioQuandoNomeEVazio() {
        var professor = new Professor();

        Long id = 1L;
        professor.setId(id);

        String mensagem = "Professor (ID: " + id + "): Validação falhou. O "
                + "campo obrigatório 'nome' estava nulo ou vazio.";

        var excecao = assertThrows(ExcecaoDominio.class, () ->
                professor.setNome("")
        );

        assertEquals(
                CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                excecao.pegarCodigoErro()
        );
        assertEquals(mensagem, excecao.getMessage());
    }

    @Test
    void emDefinirMatriculaDeveLancarExcecaoDominioQuandoMatriculaENula() {
        var professor = new Professor();

        Long id = 1L;
        professor.setId(id);

        String mensagem = "Professor (ID: " + id + "): Validação falhou. O "
                + "campo obrigatório 'matrícula' estava nulo ou vazio.";

        var excecao = assertThrows(ExcecaoDominio.class, () ->
                professor.setMatricula(null)
        );

        assertEquals(
                CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                excecao.pegarCodigoErro()
        );
        assertEquals(mensagem, excecao.getMessage());
    }

    @Test
    void emDefinirMatriculaDeveLancarExcecaoDominioQuandoMatriculaEVazia() {
        var professor = new Professor();

        Long id = 1L;
        professor.setId(id);

        String mensagem = "Professor (ID: " + id + "): Validação falhou. O "
                + "campo obrigatório 'matrícula' estava nulo ou vazio.";

        var excecao = assertThrows(ExcecaoDominio.class, () ->
                professor.setMatricula("")
        );

        assertEquals(
                CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                excecao.pegarCodigoErro()
        );
        assertEquals(mensagem, excecao.getMessage());
    }

    @Test
    void emDefinirContaUsuarioDeveLancarExcecaoDominioQuandoContaUsuarioENulo() {
        var professor = new Professor();

        Long id = 1L;
        professor.setId(id);

        String mensagem = "Professor (ID: " + id + "): Validação falhou. O "
                + "campo obrigatório 'conta de usuário' estava nulo ou vazio.";

        var excecao = assertThrows(ExcecaoDominio.class, () ->
                professor.setContaUsuario(null)
        );

        assertEquals(
                CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                excecao.pegarCodigoErro()
        );
        assertEquals(mensagem, excecao.getMessage());
    }

    @Test
    void emDefinirCargoDeveLancarExcecaoDominioQuandoCargoENulo() {
        var professor = new Professor();

        Long id = 1L;
        professor.setId(id);

        String mensagem = "Professor (ID: " + id + "): Validação falhou. O "
                + "campo obrigatório 'cargo' estava nulo ou vazio.";

        var excecao = assertThrows(ExcecaoDominio.class, () ->
                professor.setCargo(null)
        );

        assertEquals(
                CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                excecao.pegarCodigoErro()
        );
        assertEquals(mensagem, excecao.getMessage());
    }
}