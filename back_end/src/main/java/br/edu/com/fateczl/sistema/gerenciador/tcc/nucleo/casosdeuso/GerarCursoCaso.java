package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades.AjusteTipoTcc;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.TipoTcc;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Turno;

import java.util.List;

public interface GerarCursoCaso {
    record AjusteTccEntrada(TipoTcc tipoTcc, Integer maxAlunosGrupo) {}

    record Entrada(
            String nome,
            List<Turno> turnos,
            List<Disciplina> disciplinas,
            List<AjusteTccEntrada> ajustesTcc,
            String matriculaCoordenador
    ) {}

    record Saida(
            Long id,
            String name,
            List<Turno> turnos,
            List<Disciplina> disciplinas,
            List<AjusteTipoTcc> ajustesTcc,
            String nomeCoordenador,
            String matriculaCoordenador
    ) {}

    Saida executar(Entrada entrada);
}