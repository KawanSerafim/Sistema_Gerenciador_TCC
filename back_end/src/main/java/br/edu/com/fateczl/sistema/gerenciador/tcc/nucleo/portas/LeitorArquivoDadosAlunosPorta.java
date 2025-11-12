package br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.Turno;

import java.io.InputStream;
import java.util.List;

public interface LeitorArquivoDadosAlunos {
    record DadosAlunos(String name, String matricula) {}

    record DadosArquivo(
            Integer ano,
            Integer semestre,
            Turno turno,
            List<DadosAlunos> alunos
    ) {}

    DadosArquivo ler(InputStream arquivoBruto);
}