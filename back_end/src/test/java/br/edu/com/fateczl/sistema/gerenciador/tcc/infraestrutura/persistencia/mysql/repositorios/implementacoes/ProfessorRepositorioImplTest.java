package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.implementacoes;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.mapeadores.ProfessorMapeador;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.ProfessorModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.spring.ProfessorRepositorioSpring;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Professor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfessorRepositorioImplTest {
    @Mock
    private ProfessorRepositorioSpring repositorioSpring;

    @Mock
    private ProfessorMapeador professorMapeador;

    @InjectMocks
    private ProfessorRepositorioImpl professorRepositorioImpl;

    @Test
    void deveSalvarProfessorComSucesso() {
        var professorDominio = new Professor();
        var professorModelo = new ProfessorModelo();

        when(professorMapeador.paraModelo(professorDominio))
                .thenReturn(professorModelo);

        when(professorMapeador.paraDominio(professorModelo))
                .thenReturn(professorDominio);

        when(repositorioSpring.save(professorModelo))
                .thenReturn(professorModelo);

        var professorSalvo = professorRepositorioImpl.salvar(professorDominio);

        assertNotNull(professorSalvo);
        assertEquals(professorDominio, professorSalvo);

        verify(professorMapeador, times(1)).paraModelo(professorDominio);
        verify(professorMapeador, times(1)).paraDominio(professorModelo);
        verify(repositorioSpring, times(1)).save(professorModelo);
    }

    @Test
    void deveBuscarProfessorPorMatriculaComSucesso() {
        String matricula = "Matricula de Teste";
        var professorDominio = new Professor();
        var professorModelo = new ProfessorModelo();

        when(repositorioSpring.findByMatricula(matricula))
                .thenReturn(Optional.of(professorModelo));

        when(professorMapeador.paraDominio(professorModelo))
                .thenReturn(professorDominio);

        var resultado = professorRepositorioImpl.buscarPorMatricula(matricula);

        assertTrue(resultado.isPresent());
        assertEquals(professorDominio, resultado.get());
        verify(repositorioSpring, times(1)).findByMatricula(matricula);
        verify(professorMapeador, times(1)).paraDominio(professorModelo);
    }

    @Test
    void deveBuscarProfessorPorEmailComSucesso() {
        String email = "email@teste.com";
        var professorDominio = new Professor();
        var professorModelo = new ProfessorModelo();

        when(repositorioSpring.findByContaUsuarioEmail(email))
                .thenReturn(Optional.of(professorModelo));

        when(professorMapeador.paraDominio(professorModelo))
                .thenReturn(professorDominio);

        var resultado = professorRepositorioImpl.buscarPorEmail(email);

        assertTrue(resultado.isPresent());
        assertEquals(professorDominio, resultado.get());
        verify(repositorioSpring, times(1)).findByContaUsuarioEmail(email);
        verify(professorMapeador, times(1)).paraDominio(professorModelo);
    }
}