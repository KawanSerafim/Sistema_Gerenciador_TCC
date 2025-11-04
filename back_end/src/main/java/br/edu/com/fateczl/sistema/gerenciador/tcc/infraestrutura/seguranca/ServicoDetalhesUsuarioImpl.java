package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.seguranca;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .StatusAluno;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .AdministradorRepositorio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .AlunoRepositorio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .ProfessorRepositorio;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;

public class ServicoDetalhesUsuarioPersonalizado implements UserDetailsService {
    private final AdministradorRepositorio administradorRepositorio;
    private final ProfessorRepositorio professorRepositorio;
    private final AlunoRepositorio alunoRepositorio;

    public ServicoDetalhesUsuarioPersonalizado(
            AdministradorRepositorio administradorRepositorio,
            ProfessorRepositorio professorRepositorio,
            AlunoRepositorio alunoRepositorio
    ) {
        this.administradorRepositorio = administradorRepositorio;
        this.professorRepositorio = professorRepositorio;
        this.alunoRepositorio = alunoRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername (String email) {
        var administradorOpt = administradorRepositorio.buscarPorEmail(email);

        if(administradorOpt.isPresent()) {
            var administrador = administradorOpt.get();
            List<GrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority("ROLE_ADMIN")
            );

            return new DetalhesUsuarioPersonalizado(
                    administrador.getEmailContaUsuario(),
                    administrador.getSenhaContaUsuario(),
                    authorities,
                    administrador.getStatusContaUsuario()
            );
        }

        var professorOpt = professorRepositorio.buscarPorEmail(email);

        if(professorOpt.isPresent()) {
            var professor = professorOpt.get();
            List<GrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority("ROLE_PROFESSOR")
            );

            return new DetalhesUsuarioPersonalizado(
                    professor.getEmailContaUsuario(),
                    professor.getSenhaContaUsuario(),
                    authorities,
                    professor.getStatusContaUsuario()
            );
        }

        var alunoOpt = alunoRepositorio.buscarPorEmail(email);

        if(alunoOpt.isPresent()) {
            var aluno = alunoOpt.get();

            if(aluno.getStatus() != StatusAluno.CADASTRADO) {
                throw new UsernameNotFoundException(
                        "Usuário (Aluno) não possui status CADASTRADO." + email
                );
            }

            List<GrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority("ROLE_ALUNO")
            );

            return new DetalhesUsuarioPersonalizado(
                    aluno.getEmailContaUsuario(),
                    aluno.getSenhaContaUsuario(),
                    authorities,
                    aluno.getStatusContaUsuario()
            );
        }

        throw new UsernameNotFoundException(
                "Usuário não encontrado com o email: " + email
        );
    }
}