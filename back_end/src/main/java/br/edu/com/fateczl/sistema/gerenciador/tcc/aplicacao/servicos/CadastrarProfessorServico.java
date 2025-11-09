package br.edu.com.fateczl.sistema.gerenciador.tcc.aplicacao.servicos;

import br.edu.com.fateczl.sistema.gerenciador.tcc.aplicacao.eventos
        .UsuarioPedeEmailConfirmacaoEvento;
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
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas
        .CriptografoSenhasPorta;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .AdministradorRepositorio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .AlunoRepositorio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .ProfessorRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastrarProfessorServico implements CadastrarProfessorCaso {
    private final AdministradorRepositorio administradorRepositorio;
    private final ProfessorRepositorio professorRepositorio;
    private final AlunoRepositorio alunoRepositorio;
    private final CriptografoSenhasPorta criptografoSenhas;
    private final ApplicationEventPublisher publicadoraEvento;

    public CadastrarProfessorServico(
            AdministradorRepositorio administradorRepositorio,
            ProfessorRepositorio professorRepositorio,
            AlunoRepositorio alunoRepositorio,
            CriptografoSenhasPorta criptografoSenhas,
            ApplicationEventPublisher publicadoraEvento
    ) {
        this.administradorRepositorio = administradorRepositorio;
        this.professorRepositorio = professorRepositorio;
        this.alunoRepositorio = alunoRepositorio;
        this.criptografoSenhas = criptografoSenhas;
        this.publicadoraEvento = publicadoraEvento;
    }

    @Override
    @Transactional
    public Saida executar(Entrada entrada) {
        validarUnicidadeEmail(entrada.email());
        var professor = pegarProfessor(entrada);
        var professorSalvo = professorRepositorio.salvar(professor);

        publicadoraEvento.publishEvent(
                new UsuarioPedeEmailConfirmacaoEvento(
                        professorSalvo.getEmailContaUsuario()
                )
        );

        return new Saida(
                professorSalvo.getId(),
                professorSalvo.getNome(),
                professorSalvo.getMatricula(),
                professorSalvo.getCargo()
        );
    }

    private void validarUnicidadeEmail(String email) {
        Optional<Administrador> administradorOpt = administradorRepositorio
                .buscarPorEmail(email);

        Optional<Professor> professorOpt = professorRepositorio
                .buscarPorEmail(email);

        Optional<Aluno> alunoOpt = alunoRepositorio.buscarPorEmail(email);

        if(administradorOpt.isPresent()
                || professorOpt.isPresent()
                || alunoOpt.isPresent()
        ) {
            throw new ExcecaoDominio(
                    CodigoErro.RN_002_REGISTRO_DUPLICADO,
                    "Falha de unicidade. Já existe um registro com o email = "
                    + email
            );
        }
    }

    private Professor pegarProfessor(Entrada entrada) {
        Optional<Professor> professorOpt = professorRepositorio
                .buscarPorMatricula(entrada.matricula());

        if(professorOpt.isPresent()) {
            throw new ExcecaoDominio(
                    CodigoErro.RN_002_REGISTRO_DUPLICADO,
                    "Professor: Falha de unicidade. Já existe um registro com "
                    + "matrícula = " + entrada.matricula()
            );
        }

        String senha = criptografoSenhas.criptografar(entrada.senha());

        var contaUsuario = new ContaUsuario(entrada.email(), senha);

        return new Professor(
                entrada.nome(),
                entrada.matricula(),
                contaUsuario,
                entrada.cargo()
        );
    }
}