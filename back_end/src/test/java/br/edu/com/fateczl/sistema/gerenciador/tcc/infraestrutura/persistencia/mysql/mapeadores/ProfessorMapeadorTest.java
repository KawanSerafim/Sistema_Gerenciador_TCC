package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.mapeadores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.ContaUsuarioModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia.mysql.modelos.ProfessorModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .ContaUsuario;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Professor;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .CargoProfessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfessorMapeadorTest {
    private final Long id = 1L;
    private final String nome = "Nome de Teste";
    private final String matricula = "Matricula de Teste";
    private final String email = "email@teste.com";
    private final String senha = "Senha de Teste";

    @Mock
    private ContaUsuarioMapeador contaUsuarioMapeador;

    @InjectMocks
    private ProfessorMapeador professorMapeador;

    @Test
    void deveMapearDominioParaModelo() {
        var contaUsuarioDominio = new ContaUsuario(email, senha);
        var dominio = new Professor(
                nome,
                matricula,
                contaUsuarioDominio,
                CargoProfessor.ORIENTADOR
        );
        dominio.setId(id);

        var contaUsuarioModelo = new ContaUsuarioModelo();

        when(contaUsuarioMapeador.paraModelo(contaUsuarioDominio))
                .thenReturn(contaUsuarioModelo);

        var modelo = professorMapeador.paraModelo(dominio);

        assertNotNull(modelo);
        assertEquals(dominio.getId(), modelo.getId());
        assertEquals(dominio.getNome(), modelo.getNome());
        assertEquals(dominio.getMatricula(), modelo.getMatricula());
        assertEquals(contaUsuarioModelo, modelo.getContaUsuario());
        assertEquals(dominio.getCargo(), modelo.getCargo());
    }

    @Test
    void deveMapearModeloParaDominio() {
        var contaUsuarioModelo = new ContaUsuarioModelo();
        contaUsuarioModelo.setEmail(email);
        contaUsuarioModelo.setSenha(senha);

        var modelo = new ProfessorModelo();
        modelo.setId(id);
        modelo.setNome(nome);
        modelo.setMatricula(matricula);
        modelo.setContaUsuario(contaUsuarioModelo);
        modelo.setCargo(CargoProfessor.ORIENTADOR);

        var contaUsuarioDominio = new ContaUsuario();

        when(contaUsuarioMapeador.paraDominio(contaUsuarioModelo))
                .thenReturn(contaUsuarioDominio);

        var dominio = professorMapeador.paraDominio(modelo);

        assertNotNull(dominio);
        assertEquals(modelo.getId(), dominio.getId());
        assertEquals(modelo.getNome(), dominio.getNome());
        assertEquals(modelo.getMatricula(), dominio.getMatricula());
        assertEquals(contaUsuarioDominio, dominio.getContaUsuario());
        assertEquals(modelo.getCargo(), dominio.getCargo());
    }
}