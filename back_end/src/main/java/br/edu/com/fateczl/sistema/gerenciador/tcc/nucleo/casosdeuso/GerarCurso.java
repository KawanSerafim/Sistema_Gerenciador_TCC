package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Professor;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Turno;

import java.util.List;

public interface GerarCurso {
    record Entrada(
            String nome,
            List<Turno> turnos,
            List<Disciplina> disciplinas,
            Integer maxAlunosGrupo,
            String matriculaCoordenador
    ) {}

    record Saida(
            Long id,
            String name,
            List<Turno> turnos,
            List<Disciplina> disciplinas,
            Integer maxAlunosGrupo,
            String nomeCoordenador,
            String matriculaCoordenador
    ) {}

    Saida executar(Entrada entrada);
}