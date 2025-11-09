package br.edu.com.fateczl.sistema.gerenciador.tcc.aplicacao.servicos;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.configuracoes
        .seguranca.autenticacao.ServicoTokenJwt;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso.LoginCaso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Administrador;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Aluno;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Professor;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .AdministradorRepositorio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .AlunoRepositorio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .ProfessorRepositorio;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class LoginServico implements LoginCaso {
    private final AdministradorRepositorio administradorRepositorio;
    private final ProfessorRepositorio professorRepositorio;
    private final AlunoRepositorio alunoRepositorio;
    private final ServicoTokenJwt servicoTokenJwt;

    public LoginServico(
            AdministradorRepositorio administradorRepositorio,
            ProfessorRepositorio professorRepositorio,
            AlunoRepositorio alunoRepositorio,
            ServicoTokenJwt servicoTokenJwt
    ) {
        this.administradorRepositorio = administradorRepositorio;
        this.professorRepositorio = professorRepositorio;
        this.alunoRepositorio = alunoRepositorio;
        this.servicoTokenJwt = servicoTokenJwt;
    }

    @Override
    public Saida executar(Entrada entrada) {
        verificarCampoObrigatorio(entrada.email());
        verificarCampoObrigatorio(entrada.senha());

        var administradorOpt = pegarAdministradorOpt(entrada.email());
        var professorOpt = pegarProfessorOpt(entrada.email());
        var alunoOpt = pegarAlunoOpt(entrada.email());

        String senha = null;

        if(administradorOpt.isPresent()) {
            var administrador = administradorOpt.get();
            senha = administrador.getSenhaContaUsuario();
        }

        if(professorOpt.isPresent()) {
            var professor = professorOpt.get();
            senha = professor.getSenhaContaUsuario();
        }

        if(alunoOpt.isPresent()) {
            var aluno = alunoOpt.get();
            senha = aluno.getSenhaContaUsuario();
        }

        if(senha == null) {
            throw new ExcecaoDominio(
                    CodigoErro.AU_002_CREDENCIAS_INVALIDAS,
                    "Autenticação: Falha na tentativa de login para o usuário "
                    + " [" + entrada.email() + "]. (Motivo: Credenciais "
                    + "inválidas, IP: [IP_Address])"
            );
        }
        validarSenha(senha, entrada.senha(), entrada.email());

        String token = servicoTokenJwt.gerarToken(entrada.email());

        return new Saida(token, "Bearer");
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

    private void validarSenha(
            String senhaUsuario,
            String senhaRequisicao,
            String email
    ) {
        boolean senhaBateu = Objects.equals(senhaUsuario, senhaRequisicao);

        if(!senhaBateu) {
            throw new ExcecaoDominio(
                    CodigoErro.AU_002_CREDENCIAS_INVALIDAS,
                    "Autenticação: Falha na tentativa de login para o usuário "
                    + " [" + email + "]. (Motivo: Credenciais inválidas, IP: "
                    + "[IP_Address])"
            );
        }
    }

    private Optional<Administrador> pegarAdministradorOpt(String email) {
        return administradorRepositorio.buscarPorEmail(email);
    }

    private Optional<Professor> pegarProfessorOpt(String email) {
        return professorRepositorio.buscarPorEmail(email);
    }

    private Optional<Aluno> pegarAlunoOpt(String email) {
        return alunoRepositorio.buscarPorEmail(email);
    }
}