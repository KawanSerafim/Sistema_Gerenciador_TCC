package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede
        .controladores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .requisicoes.GerarTurmaRequisicao;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .respostas.GerarTurmaResposta;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso
        .GerarTurmaCaso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/turma/api")
@CrossOrigin(origins = "*")
public class TurmaControlador {
    private final GerarTurmaCaso gerarTurmaCaso;

    public TurmaControlador(GerarTurmaCaso gerarTurmaCaso) {
        this.gerarTurmaCaso = gerarTurmaCaso;
    }

    @PostMapping("/gerar")
    @PreAuthorize("hasRole('ROLE_COORDENADOR_CURSO')")
    public ResponseEntity<GerarTurmaResposta> gerar(
            @RequestBody GerarTurmaRequisicao requisicao,
            Authentication authentication
    ) {
        var entrada = new GerarTurmaCaso.Entrada(
                authentication.getName(),
                requisicao.disciplina(),
                requisicao.turno(),
                requisicao.matriculaProfessorTg(),
                requisicao.ano(),
                requisicao.semestre()
        );

        var resultado = gerarTurmaCaso.executar(entrada);

        var corpoResposta = new GerarTurmaResposta(
                resultado.idTurma(),
                resultado.idCurso(),
                resultado.disciplina(),
                resultado.turno(),
                resultado.idProfessorTg(),
                resultado.ano(),
                resultado.semestre()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(corpoResposta);
    }
}