package br.edu.com.fateczl.sistema.gerenciador.tcc.aplicacao.servicos;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso
        .CadastrarProfessorCaso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Administrador;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Aluno;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .ContaUsuario;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Professor;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .CargoProfessor;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.CriptografoSenhasPorta;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .AdministradorRepositorio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .AlunoRepositorio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .ProfessorRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CadastrarProfessorServicoTest {
    @Mock
    private AdministradorRepositorio administradorRepositorio;

    @Mock
    private ProfessorRepositorio professorRepositorio;

    @Mock
    private AlunoRepositorio alunoRepositorio;

    @Mock
    private CriptografoSenhasPorta criptografoSenhas;

    @InjectMocks
    private CadastrarProfessorServico servico;

    private CadastrarProfessorCaso.Entrada entrada;

    private enum TipoDuplicidade {
        EMAIL_EM_ALUNO,
        EMAIL_EM_PROFESSOR,
        EMAIL_EM_ADMINISTRADOR
    }

    @BeforeEach
    void setUp() {
        this.entrada = new CadastrarProfessorCaso.Entrada(
                "Nome de Teste",
                "Matrícula de Teste",
                "email@teste.com",
                "senha de teste",
                CargoProfessor.ORIENTADOR
        );
    }

    @Test
    void deveCadastrarProfessorComDadosValidos() {
        var contaUsuario = mock(ContaUsuario.class);
        String senhaCriptografada = "senha criptografada";

        var professorSalvo = new Professor(
                entrada.nome(),
                entrada.matricula(),
                contaUsuario,
                entrada.cargo()
        );
        professorSalvo.setId(1L);

        when(professorRepositorio.salvar(any(Professor.class)))
                .thenReturn(professorSalvo);
        when(criptografoSenhas.criptografar(entrada.senha()))
                .thenReturn(senhaCriptografada);

        var saida = servico.executar(entrada);

        assertNotNull(saida);
        assertEquals(1L, saida.id());
        assertEquals(entrada.nome(), saida.nome());
        assertEquals(entrada.matricula(), saida.matricula());
        assertEquals(entrada.cargo(), saida.cargo());

        verify(administradorRepositorio, times(1))
                .buscarPorEmail(entrada.email());
        verify(professorRepositorio, times(1)).buscarPorEmail(entrada.email());
        verify(alunoRepositorio, times(1)).buscarPorEmail(entrada.email());
        verify(professorRepositorio, times(1)).salvar(any(Professor.class));
    }

    @ParameterizedTest
    @EnumSource(TipoDuplicidade.class)
    void deveLancarExcecaoDominioQuandoEmailJaExistir(TipoDuplicidade tipo) {
        String mensagem = "Falha de unicidade. Já existe um registro com o "
                + "email = " + entrada.email();

        switch(tipo){
            case EMAIL_EM_ADMINISTRADOR:
                when(administradorRepositorio.buscarPorEmail(entrada.email()))
                        .thenReturn(Optional.of(new Administrador()));
                break;
            case EMAIL_EM_PROFESSOR:
                when(professorRepositorio.buscarPorEmail(entrada.email()))
                        .thenReturn(Optional.of(new Professor()));
                break;
            case EMAIL_EM_ALUNO:
                when(alunoRepositorio.buscarPorEmail(entrada.email()))
                        .thenReturn(Optional.of(new Aluno()));
                break;
        }

        var excecao = assertThrows(ExcecaoDominio.class, () ->
                servico.executar(entrada)
        );

        assertEquals(
                CodigoErro.RN_002_REGISTRO_DUPLICADO,
                excecao.pegarCodigoErro()
        );
        assertEquals(mensagem, excecao.getMessage());
        verify(professorRepositorio, never()).salvar(any(Professor.class));
    }

    @Test
    void deveLancarExcecaoDominioQuandoProfessorJaExistir() {
        String mensagem = "Professor: Falha de unicidade. Já existe um registro"
                + " com matrícula = " + entrada.matricula();

        when(professorRepositorio.buscarPorMatricula(entrada.matricula()))
                .thenReturn(Optional.of(new Professor()));

        var excecao = assertThrows(ExcecaoDominio.class, () ->
                servico.executar(entrada)
        );

        assertEquals(
                CodigoErro.RN_002_REGISTRO_DUPLICADO,
                excecao.pegarCodigoErro()
        );
        assertEquals(mensagem, excecao.getMessage());
        verify(professorRepositorio, never()).salvar(any(Professor.class));
    }
}