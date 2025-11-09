package br.edu.com.fateczl.sistema.gerenciador.tcc.aplicacao.servicos;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso
        .ValidarCodigoCaso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Administrador;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Aluno;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Professor;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .StatusContaUsuario;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas
        .CacheCodigoPorta;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .AdministradorRepositorio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .AlunoRepositorio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .ProfessorRepositorio;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidarCodigoServico implements ValidarCodigoCaso {
    private final AdministradorRepositorio administradorRepositorio;
    private final ProfessorRepositorio professorRepositorio;
    private final AlunoRepositorio alunoRepositorio;
    private final CacheCodigoPorta cacheCodigo;

    public ValidarCodigoServico(
            AdministradorRepositorio administradorRepositorio,
            ProfessorRepositorio professorRepositorio,
            AlunoRepositorio alunoRepositorio,
            CacheCodigoPorta cacheCodigo
    ) {
        this.administradorRepositorio = administradorRepositorio;
        this.professorRepositorio = professorRepositorio;
        this.alunoRepositorio = alunoRepositorio;
        this.cacheCodigo = cacheCodigo;
    }

    @Override
    @Transactional
    public void executar(Entrada entrada) {
        String email = cacheCodigo.pegarEmailPeloCodigo(entrada.codigo())
                .orElseThrow(() -> new ExcecaoDominio(
                        CodigoErro.GN_001_REGISTRO_NAO_ENCONTRADO,
                        "Conta Usuário: Não encontrado. Nenhuma entidade "
                        + "localizada com o critério: [código de confirmação]"
                        + " = '[" + entrada.codigo() + "]'."
                ));

        var administradorOpt = pegarAdministrador(email);
        var professorOpt = pegarProfessor(email);
        var alunoOpt = pegarAluno(email);

        procurarUsuario(administradorOpt, professorOpt, alunoOpt);

        administradorOpt.ifPresent(this::atualizarAdministrador);
        professorOpt.ifPresent(this::atualizarProfessor);
        alunoOpt.ifPresent(this::atualizarAluno);

        cacheCodigo.removerCodigo(entrada.codigo());
    }

    private void procurarUsuario(
            Optional<Administrador> administradorOpt,
            Optional<Professor> professorOpt,
            Optional<Aluno> alunoOpt
    ) {
        if(administradorOpt.isEmpty()
                && professorOpt.isEmpty()
                && alunoOpt.isEmpty()
        ) {
            throw new ExcecaoDominio(
                    CodigoErro.GN_001_REGISTRO_NAO_ENCONTRADO,
                    "Conta Usuário: Não encontrado. Nenhuma entidade "
                    + "localizada com o critério: [email cadastrado]"
                    + " = '[email encontrado pelo código]'."
            );
        }
    }

    private void atualizarAdministrador(Administrador administrador) {
        var contaUsuario = administrador.getContaUsuario();
        validarStatusContaUsuario(contaUsuario.getStatus());
        contaUsuario.setStatus(StatusContaUsuario.ATIVO);
        administrador.setContaUsuario(contaUsuario);

        administradorRepositorio.salvar(administrador);
    }

    private void atualizarProfessor(Professor professor) {
        var contaUsuario = professor.getContaUsuario();
        validarStatusContaUsuario(contaUsuario.getStatus());
        contaUsuario.setStatus(StatusContaUsuario.ATIVO);
        professor.setContaUsuario(contaUsuario);

        professorRepositorio.salvar(professor);
    }

    private void atualizarAluno(Aluno aluno) {
        var contaUsuario = aluno.getContaUsuario();
        validarStatusContaUsuario(contaUsuario.getStatus());
        contaUsuario.setStatus(StatusContaUsuario.EMAIL_CONFIRMADO);
        aluno.setContaUsuario(contaUsuario);

        alunoRepositorio.salvar(aluno);
    }

    private void validarStatusContaUsuario(StatusContaUsuario status) {
        if(status != StatusContaUsuario.VERIFICACAO_PENDENTE) {
            throw new ExcecaoDominio(
                    CodigoErro.RN_001_ESTADO_INVALIDO_PARA_ACAO,
                    "Conta Usuário: Ação '[Ação]' falhou devido a estado "
                    + "inválido. (EstadoAtual: '[ " + status + "]', Esperado: "
                    + "'[VERIFICACAO_PENDENTE]')"
            );
        }
    }

    private Optional<Administrador> pegarAdministrador(String email) {
        return administradorRepositorio.buscarPorEmail(email);
    }

    private Optional<Professor> pegarProfessor(String email) {
        return professorRepositorio.buscarPorEmail(email);
    }

    private Optional<Aluno> pegarAluno(String email) {
        return alunoRepositorio.buscarPorEmail(email);
    }
}