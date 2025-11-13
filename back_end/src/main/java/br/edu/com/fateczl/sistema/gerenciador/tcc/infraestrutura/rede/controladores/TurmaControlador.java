package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede
        .controladores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .requisicoes.GerarTurmaRequisicao;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .respostas.ExportarAlunosResposta;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .respostas.GerarTurmaResposta;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso
        .ExportarAlunosCaso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso
        .GerarTurmaCaso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/turma/api")
@CrossOrigin(origins = "*")
public class TurmaControlador {
    private final GerarTurmaCaso gerarTurmaCaso;
    private final ExportarAlunosCaso exportarAlunosCaso;

    public TurmaControlador(
            GerarTurmaCaso gerarTurmaCaso,
            ExportarAlunosCaso exportarAlunosCaso
    ) {
        this.gerarTurmaCaso = gerarTurmaCaso;
        this.exportarAlunosCaso = exportarAlunosCaso;
    }

    @PostMapping("/gerar")
    @PreAuthorize("hasRole('ROLE_COORDENADOR_CURSO')")
    public ResponseEntity<GerarTurmaResposta> gerar(
            @RequestBody GerarTurmaRequisicao requisicao,
            Authentication autenticacao
    ) {
        var entrada = new GerarTurmaCaso.Entrada(
                autenticacao.getName(),
                requisicao.disciplina(),
                requisicao.turno(),
                requisicao.matriculaProfessorTg(),
                requisicao.ano(),
                requisicao.semestre()
        );

        var resultado = gerarTurmaCaso.executar(entrada);

        var corpoResposta = new GerarTurmaResposta(
                resultado.idTurma(),
                resultado.nomeCurso(),
                resultado.disciplina(),
                resultado.turno(),
                resultado.nomeProfessorTg(),
                resultado.ano(),
                resultado.semestre()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(corpoResposta);
    }

    @PostMapping("/{idTurma}/exportar-alunos")
    @PreAuthorize("hasRole('PROFESSOR_TG')")
    public ResponseEntity<ExportarAlunosResposta> exportarAlunos(
            @PathVariable Long idTurma,
            @RequestParam("arquivo") MultipartFile arquivo,
            Authentication autenticacao
    ) throws IOException {
        var entrada = new ExportarAlunosCaso.Entrada(
                autenticacao.getName(),
                idTurma,
                arquivo.getInputStream()
        );

        var resultado = exportarAlunosCaso.executar(entrada);

        var corpoResposta = new ExportarAlunosResposta(
                resultado.idTurma(),
                resultado.nomeCurso(),
                resultado.turno(),
                resultado.disciplina(),
                resultado.ano(),
                resultado.semestre(),
                resultado.alunos()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(corpoResposta);
    }
}