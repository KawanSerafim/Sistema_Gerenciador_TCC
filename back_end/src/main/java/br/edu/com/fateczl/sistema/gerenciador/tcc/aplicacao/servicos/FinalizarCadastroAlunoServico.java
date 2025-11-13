package br.edu.com.fateczl.sistema.gerenciador.tcc.aplicacao.servicos;

import br.edu.com.fateczl.sistema.gerenciador.tcc.aplicacao.eventos.UsuarioPedeEmailConfirmacaoEvento;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso
        .FinalizarCadastroAlunoCaso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades.Administrador;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades.Aluno;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades.ContaUsuario;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades.Professor;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.StatusAluno;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes.CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes.ExcecaoDominio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas
        .CriptografoSenhasPorta;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .AdministradorRepositorio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .AlunoRepositorio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .ProfessorRepositorio;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FinalizarCadastroAlunoServico
        implements FinalizarCadastroAlunoCaso {
    private final AdministradorRepositorio administradorRepositorio;
    private final ProfessorRepositorio professorRepositorio;
    private final AlunoRepositorio alunoRepositorio;
    private final CriptografoSenhasPorta criptografoSenhas;
    private final ApplicationEventPublisher publicadoraEvento;

    public FinalizarCadastroAlunoServico(
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
    public void executar(Entrada entrada) {
        validarUnicidadeEmail(entrada.email());
        var aluno = pegarAluno(entrada);
        var alunoSalvo = alunoRepositorio.salvar(aluno);

        publicadoraEvento.publishEvent(
                new UsuarioPedeEmailConfirmacaoEvento(
                        alunoSalvo.getEmailContaUsuario()
                )
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

    private Aluno pegarAluno(Entrada entrada) {
        Aluno aluno = alunoRepositorio.buscarPorMatricula(entrada.matricula())
                .orElseThrow(() -> new ExcecaoDominio(
                        CodigoErro.GN_001_REGISTRO_NAO_ENCONTRADO,
                        "[Aluno]: Não encontrado. Nenhuma entidade localizada "
                        + " com o critério: [matrícula] = '[ "
                        + entrada.matricula() + "]'."
                ));

        if(aluno.getStatus() != StatusAluno.PRE_CADASTRO) {
            throw new ExcecaoDominio(
                    CodigoErro.RN_001_ESTADO_INVALIDO_PARA_ACAO,
                    "[Aluno] (ID: [" + aluno.getId() + "]): Ação '[Finalizar "
                    + "cadastro]' falhou devido a estado inválido. "
                    + "(EstadoAtual: '[" + aluno.getStatus() + "]', Esperado: "
                    + "'[PRE_CADASTRO]')"
            );
        }

        String senha = criptografoSenhas.criptografar(entrada.senha());

        var contaUsuario = new ContaUsuario(entrada.email(), senha);

        aluno.setContaUsuario(contaUsuario);

        return aluno;
    }
}