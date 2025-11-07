package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .StatusAluno;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .StatusContaUsuario;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Aluno {
    private Long id;
    private String nome;
    private String matricula;
    private ContaUsuario contaUsuario;
    private StatusAluno status;
    private List<Turma> turmas = new ArrayList<>();

    public Aluno() {}

    public Aluno(
            String nome,
            String matricula,
            Turma turmaInicial
    ) {
        this.setNome(nome);
        this.setMatricula(matricula);
        matricularEmTurma(turmaInicial);
    }

    public void matricularEmTurma(Turma novaTurma) {
        if(novaTurma == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "Aluno (ID: " + getId() + "): Validação falhou. O campo "
                    + "obrigatório 'turma' estava nulo ou vazio."
            );
        }

        boolean jaMatriculado = this.getTurmas().stream()
                .anyMatch(turmaExistente -> Objects.equals(
                        turmaExistente.getId(), novaTurma.getId()
                ));

        if(jaMatriculado) {
            throw new ExcecaoDominio(
                    CodigoErro.RN_002_REGISTRO_DUPLICADO,
                    "Aluno: Falha de unicidade. Já existe um registro de aluno "
                    + " (ID: " + getId() + ") nesta turma (ID: "
                    + novaTurma.getId() + ")"
            );
        }
        this.turmas.add(novaTurma);
    }

    public void finalizarCadastro(ContaUsuario contaUsuario) {
        if(contaUsuario.getStatus() != StatusContaUsuario.EMAIL_CONFIRMADO) {
            throw new ExcecaoDominio(
                    CodigoErro.RN_001_ESTADO_INVALIDO_PARA_ACAO,
                    "Aluno (ID: " + getId() + "): Ação 'finalizar cadastro' "
                    + "falhou devido a estado inválido. (EstadoAtual: '"
                    + contaUsuario.getStatus() + "', Esperado: '"
                    + StatusContaUsuario.EMAIL_CONFIRMADO + "')"
            );
        }
        this.setContaUsuario(contaUsuario);
        this.getContaUsuario().setStatus(StatusContaUsuario.ATIVO);
        this.setStatus(StatusAluno.CADASTRADO);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(nome == null || nome.isBlank()) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "Aluno (ID: " + getId() + "): Validação falhou. O campo "
                    + "obrigatório 'nome' estava nulo ou vazio."
            );
        }
        this.nome = nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        if(matricula == null || matricula.isBlank()) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "Aluno (ID: " + getId() + "): Validação falhou. O campo "
                    + "obrigatório 'matrícula' estava nulo ou vazio."
            );
        }
        this.matricula = matricula;
    }

    public ContaUsuario getContaUsuario() {
        return contaUsuario;
    }

    public void setContaUsuario(ContaUsuario contaUsuario) {
        if(contaUsuario == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "Aluno (ID: " + getId() + "): Validação falhou. O campo "
                    + "obrigatório 'conta de usuário' estava nulo ou vazio."
            );
        }
        this.contaUsuario = contaUsuario;
    }

    public StatusAluno getStatus() {
        return status;
    }

    public void setStatus(StatusAluno status) {
        if(status == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "Aluno (ID: " + getId() + "): Validação falhou. O campo "
                    + "obrigatório 'status' estava nulo ou vazio."
            );
        }
        this.status = status;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<Turma> turmas) {
        if(turmas == null || turmas.isEmpty()) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "Aluno (ID: " + getId() + "): Validação falhou. O campo "
                    + "obrigatório 'turmas' estava nulo ou vazio."
            );
        }
        this.turmas = turmas;
    }

    public String getEmailContaUsuario() {
        return contaUsuario.getEmail();
    }

    public String getSenhaContaUsuario() {
        return contaUsuario.getSenha();
    }

    public StatusContaUsuario getStatusContaUsuario() {
        return contaUsuario.getStatus();
    }
}