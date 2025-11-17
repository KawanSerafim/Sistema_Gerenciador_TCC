package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Aluno;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .GrupoTcc;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Turma;

import java.util.List;
import java.util.Optional;

public interface GrupoTccRepositorio {
    GrupoTcc salvar(GrupoTcc grupoTcc);

    Optional<GrupoTcc> buscarPorAlunosETurma(List<Aluno> alunos, Turma turma);
}