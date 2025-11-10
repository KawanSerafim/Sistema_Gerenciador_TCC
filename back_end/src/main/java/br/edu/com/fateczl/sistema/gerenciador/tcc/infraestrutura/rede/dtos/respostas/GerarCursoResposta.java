package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .respostas;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.Turno;

import java.util.List;

public record GerarCursoResposta(
        Long id,
        String name,
        List<Turno> turnos,
        List<Disciplina> disciplinas,
        Integer maxAlunosGrupo,
        String nomeCoordenador,
        String matriculaCoordenador
) {}