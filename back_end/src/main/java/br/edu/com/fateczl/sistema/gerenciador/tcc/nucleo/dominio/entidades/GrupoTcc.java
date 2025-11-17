package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.TipoTcc;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;

import java.util.List;

public class GrupoTcc {
    private Long id;
    private Professor orientador;
    private Coorientador coorientador;
    private String tema;
    private TipoTcc tipoTcc;
    private Disciplina disciplina;
    private List<Aluno> alunos;
    private Turma turma;

    public GrupoTcc() {}

    public GrupoTcc(
            Professor orientador,
            Coorientador coorientador,
            String tema,
            TipoTcc tipoTcc,
            Disciplina disciplina,
            List<Aluno> alunos,
            Turma turma
    ) {
        this.setTema(tema);
        this.setTipoTcc(tipoTcc);
        this.setDisciplina(disciplina);
        this.setAlunos(alunos);
        this.setTurma(turma);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Professor getOrientador() {
        return orientador;
    }

    public void setOrientador(Professor orientador) {
        if(orientador == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "[GrupoTcc] (ID: [" + this.getId() + "]): Validação falhou."
                    + "O campo obrigatório '[Orientador]' estava nulo ou vazio."
            );
        }
        if(!orientador.podeSerOrientador()) {
            throw new ExcecaoDominio(
                    CodigoErro.RN_001_ESTADO_INVALIDO_PARA_ACAO,
                    "[GrupoTcc] (ID: [" + this.getId() + "]): Ação '["
                    + "Definir um orientador num grupo de TCC]' falhou devido "
                    + "a estado inválido. (EstadoAtual: '["
                    + orientador.getCargo() + "]', Esperado: '[ORIENTADOR, "
                    + "PROFESSOR_TG OU COORDENADOR_CURSO]')"
            );
        }
        this.orientador = orientador;
    }

    public Coorientador getCoorientador() {
        return coorientador;
    }

    public void setCoorientador(Coorientador coorientador) {
        if(coorientador == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "[GrupoTcc] (ID: [" + this.getId() + "]): Validação falhou."
                    + "O campo obrigatório '[Coorientador]' estava nulo ou "
                    + "vazio."
            );
        }
        this.coorientador = coorientador;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        if(tema == null || tema.isBlank()) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "[GrupoTcc] (ID: [" + this.getId() + "]): Validação falhou."
                    + "O campo obrigatório '[Tema]' estava nulo ou vazio."
            );
        }
        this.tema = tema;
    }

    public TipoTcc getTipoTcc() {
        return tipoTcc;
    }

    public void setTipoTcc(TipoTcc tipoTcc) {
        if(tipoTcc == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "[GrupoTcc] (ID: [" + this.getId() + "]): Validação falhou."
                    + "O campo obrigatório '[Tipo de TCC]' estava nulo ou "
                    + "vazio."
            );
        }
        this.tipoTcc = tipoTcc;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        if(disciplina == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "[GrupoTcc] (ID: [" + this.getId() + "]): Validação falhou."
                    + "O campo obrigatório '[Disciplina]' estava nulo ou vazio."
            );
        }
        this.disciplina = disciplina;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        if(alunos == null || alunos.isEmpty()) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "[GrupoTcc] (ID: [" + this.getId() + "]): Validação falhou."
                    + "O campo obrigatório '[Alunos]' estava nulo ou vazio."
            );
        }
        this.alunos = alunos;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        if(turma == null) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_001_CAMPO_OBRIGATORIO,
                    "[GrupoTcc] (ID: [" + this.getId() + "]): Validação falhou."
                    + "O campo obrigatório '[Turma]' estava nulo ou vazio."
            );
        }
        this.turma = turma;
    }
}