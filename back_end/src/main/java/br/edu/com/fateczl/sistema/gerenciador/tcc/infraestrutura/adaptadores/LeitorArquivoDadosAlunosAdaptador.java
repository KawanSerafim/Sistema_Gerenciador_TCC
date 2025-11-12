package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.adaptadores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Turno;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .CodigoErro;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.LeitorArquivoDadosAlunosPorta;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class LeitorArquivoDadosAlunosAdaptador
        implements LeitorArquivoDadosAlunosPorta {
    @Override
    public DadosArquivo ler(InputStream arquivoBruto) {
        try(Workbook workbook = new XSSFWorkbook(arquivoBruto)) {
            Sheet planilha = workbook.getSheetAt(0);
            Row cabecalho = planilha.getRow(0);

            String celulaAnoSemestre = pegarValorStringDaCelula(
                    cabecalho.getCell(0)
            );

            int anoSemestreInt = Integer.parseInt(celulaAnoSemestre);
            int ano = anoSemestreInt / 10;
            int semestre = anoSemestreInt % 10;

            Cell celulaTurno = cabecalho.getCell(2);
            Turno turno = pegarTurno(celulaTurno);

            List<DadosAlunos> alunos = new ArrayList<>();

            for(int i = 2; i <= planilha.getLastRowNum(); i++) {
                Row linha = planilha.getRow(i);

                if(linha == null) continue;

                String nome = pegarValorStringDaCelula(linha.getCell(1));
                String matricula = pegarValorStringDaCelula(linha.getCell(0));

                if(!nome.isBlank() && !matricula.isBlank()) {
                    alunos.add(new DadosAlunos(nome, matricula));
                }
            }
            return new DadosArquivo(ano, semestre, turno, alunos);
        } catch (Exception e) {
            throw new ExcecaoDominio(
                    CodigoErro.VD_008_ARQUIVO_INVALIDO,
                    "Leitor de Arquivo: Falha ao processar o arquivo ['Excel']:"
                    + e.getMessage()
            );
        }
    }

    private String pegarValorStringDaCelula(Cell celula) {
        if(celula == null) return "";

        if(celula.getCellType() == CellType.NUMERIC) {
            return new DataFormatter().formatCellValue(celula);
        }
        return celula.getStringCellValue();
    }

    private Turno pegarTurno(Cell celulaTurno) {
        String celulaTurnoString = celulaTurno.getStringCellValue()
                .toUpperCase();

        if(celulaTurnoString.contains("MANHÃ")){
            return Turno.MANHA;
        }
        if(celulaTurnoString.contains("TARDE")){
            return Turno.TARDE;
        }
        if(celulaTurnoString.contains("NOITE")){
            return Turno.NOITE;
        }

        throw new ExcecaoDominio(
                CodigoErro.VD_007_CAMPO_NAO_SUPORTADO,
                "Leitor de Arquivo: O campo '[Turno]' não é suportado "
                + "[Texto do turno não foi reconhecido]."
        );
    }
}