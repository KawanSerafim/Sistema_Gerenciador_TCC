package br.edu.com.fateczl.sistema.gerenciador.tcc.integracao;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .requisicoes.ProfessorRequisicao;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .CargoProfessor;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .ProfessorRepositorio;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet
        .AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request
        .MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers
        .jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers
        .status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class CadastrarProfessorIntegracaoTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProfessorRepositorio professorRepositorio;

    @Test
    void deveCadastrarProfessorComSucesso() throws Exception {
        var requisicao = new ProfessorRequisicao(
                "Professor Teste",
                "P12345",
                "professor.teste@fatec.com.br",
                "senha123",
                CargoProfessor.ORIENTADOR
        );

        String corpoJson = objectMapper.writeValueAsString(requisicao);

        mockMvc.perform(post("/professores/api/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(corpoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Professor Teste"))
                .andExpect(jsonPath("$.matricula").value("P12345"));

        var professorSalvoOpt = professorRepositorio
                .buscarPorMatricula("P12345");
        assertTrue(professorSalvoOpt.isPresent());
        assertEquals(
                "professor.teste@fatec.com.br",
                professorSalvoOpt.get().getEmailContaUsuario()
        );
    }

    @Test
    void naoDeveCadastrarProfessorComMatriculaDuplicada() throws Exception {
        var requisicao1 = new ProfessorRequisicao(
                "Professor 1",
                "P11111",
                "email1@fatec.com.br",
                "senha",
                CargoProfessor.COORDENADOR_CURSO
        );

        mockMvc.perform(post("/professores/api/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requisicao1)))
                .andExpect(status().isCreated());

        var requisicaoDuplicada = new ProfessorRequisicao(
                "Professor 2",
                "P11111",
                "email2@fatec.com.br",
                "senha123",
                CargoProfessor.ORIENTADOR
        );

        mockMvc.perform(post("/professores/api/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                requisicaoDuplicada
                        )))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value(
                        CodigoErro.RN_002_REGISTRO_DUPLICADO.toString()
                ));
    }

    @Test
    void naoDeveCadastrarProfessorComEmailDuplicado() throws Exception {
        var requisicao1 = new ProfessorRequisicao(
                "Professor 1",
                "P11111",
                "email.duplicado@fatec.com.br",
                "senha",
                CargoProfessor.COORDENADOR_CURSO
        );

        mockMvc.perform(post("/professores/api/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requisicao1)))
                .andExpect(status().isCreated());

        var requisicaoDuplicada = new ProfessorRequisicao(
                "Professor 2",
                "P22222",
                "email.duplicado@fatec.com.br",
                "senha123",
                CargoProfessor.ORIENTADOR
        );

        mockMvc.perform(post("/professores/api/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                requisicaoDuplicada
                        )))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value(
                        CodigoErro.RN_002_REGISTRO_DUPLICADO.toString()
                ));
    }
}