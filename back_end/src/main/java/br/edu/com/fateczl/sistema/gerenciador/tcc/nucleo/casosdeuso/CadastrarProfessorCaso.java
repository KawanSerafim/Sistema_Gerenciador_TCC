package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .CargoProfessor;

public interface CadastrarProfessorCaso {
    record Entrada(
            String nome,
            String matricula,
            String email,
            String senha,
            CargoProfessor cargo
    ) {}

    record Saida(
            Long id,
            String nome,
            String matricula,
            CargoProfessor cargo
    ) {}

    Saida executar(Entrada entrada);
}