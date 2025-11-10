package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede
        .controladores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .requisicoes.GerarCursoRequisicao;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .respostas.GerarCursoResposta;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso
        .GerarCursoCaso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/curso/api")
@CrossOrigin(origins = "*")
public class CursoControlador {
    private final GerarCursoCaso gerarCursoCaso;

    public CursoControlador(GerarCursoCaso gerarCursoCaso) {
        this.gerarCursoCaso = gerarCursoCaso;
    }

    @PostMapping("/gerar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GerarCursoResposta> gerar(
            @RequestBody GerarCursoRequisicao requisicao
    ) {
        var entrada = new GerarCursoCaso.Entrada(
                requisicao.nome(),
                requisicao.turnos(),
                requisicao.disciplinas(),
                requisicao.maxAlunosGrupo(),
                requisicao.matriculaCoordenador()
        );

        var resultado = gerarCursoCaso.executar(entrada);

        var corpoResposta = new GerarCursoResposta(
                resultado.id(),
                resultado.name(),
                resultado.turnos(),
                resultado.disciplinas(),
                resultado.maxAlunosGrupo(),
                resultado.nomeCoordenador(),
                resultado.matriculaCoordenador()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(corpoResposta);
    }
}