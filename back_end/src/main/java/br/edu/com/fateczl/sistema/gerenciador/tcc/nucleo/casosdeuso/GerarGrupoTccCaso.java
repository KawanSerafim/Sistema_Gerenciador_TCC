package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.TipoTcc;

import java.util.List;

public interface GerarGrupoTccCaso {
    record Entrada(
            String tema,
            TipoTcc tipoTcc,
            Disciplina disciplina,
            List<String> matriculasAluno,
            Long idTurma
    ) {}

    void executar(Entrada entrada);
}