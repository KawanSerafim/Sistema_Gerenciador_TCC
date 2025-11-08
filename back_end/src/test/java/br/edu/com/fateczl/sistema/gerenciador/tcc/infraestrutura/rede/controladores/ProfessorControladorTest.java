package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede
        .controladores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede
        .dtos.requisicoes.ProfessorRequisicao;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede
        .excecoes.ManipuladorGlobalExcecao;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso
        .CadastrarProfessorCaso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .CargoProfessor;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request
        .MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers
        .jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers
        .status;

@ExtendWith(MockitoExtension.class)
public class ProfessorControladorTest {
    private final String nome = "Nome de Teste";
    private final String matricula = "Matricula de Teste";
    private final String email = "email@teste.com";
    private final String senha = "Senha de Teste";

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CadastrarProfessorCaso cadastrarProfessorCaso;

    @InjectMocks
    private ProfessorControlador professorControlador;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(professorControlador)
                .setControllerAdvice(new ManipuladorGlobalExcecao())
                .build();
    }

    @Test
    void deveCadastrarERetornarStatusCreated() throws Exception {
        Long id = 1L;

        var requisicao = new ProfessorRequisicao(
                nome,
                matricula,
                email,
                senha,
                CargoProfessor.ORIENTADOR
        );

        var saida = new CadastrarProfessorCaso.Saida(
                id,
                nome,
                matricula,
                CargoProfessor.ORIENTADOR
        );

        when(cadastrarProfessorCaso.executar(
                any(CadastrarProfessorCaso.Entrada.class)))
                .thenReturn(saida);

        mockMvc.perform(post("/professores/api/cadastrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requisicao)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value(nome));
    }

    @Test
    void deveRetornarBadRequestQuandoServicoLancarExcecaoDominio()
            throws Exception {
        var requisicao = new ProfessorRequisicao(
                nome,
                matricula,
                email,
                senha,
                CargoProfessor.ORIENTADOR
        );

        when(cadastrarProfessorCaso.executar(
                any(CadastrarProfessorCaso.Entrada.class)))
                .thenThrow(new ExcecaoDominio(
                        CodigoErro.RN_002_REGISTRO_DUPLICADO,
                        "Falha de unicidade. Já existe um registro com o email "
                        + "= " + email
                ));

        mockMvc.perform(post("/professores/api/cadastrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requisicao)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value(
                        "RN_002_REGISTRO_DUPLICADO"
                ));
    }
}