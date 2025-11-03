package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .CargoProfessor;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.StatusContaUsuario;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;

public class Professor {
    private Long id;
    private String nome;
    private String matricula;
    private ContaUsuario contaUsuario;
    private CargoProfessor cargo;

    public Professor() {}

    public Professor(
            String nome,
            String matricula,
            ContaUsuario contaUsuario,
            CargoProfessor cargo
    ) {
        this.setNome(nome);
        this.setMatricula(matricula);
        this.setContaUsuario(contaUsuario);
        this.setCargo(cargo);
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
                    "Professor (ID: " + getId() + "): Validação falhou. O campo"
                    + " obrigatório 'nome' estava nulo ou vazio."
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
                    "Professor (ID: " + getId() + "): Validação falhou. O campo"
                    + " obrigatório 'matrícula' estava nulo ou vazio."
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
                    "Professor (ID: " + getId() + "): Validação falhou. O campo"
                    + " obrigatório 'conta de usuário' estava nulo ou vazio."
            );
        }
        this.contaUsuario = contaUsuario;
    }

    public CargoProfessor getCargo() {
        return cargo;
    }

    public void setCargo(CargoProfessor cargo) {
        if(cargo == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "Professor (ID: " + getId() + "): Validação falhou. O campo"
                    + " obrigatório 'cargo' estava nulo ou vazio."
            );
        }
        this.cargo = cargo;
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