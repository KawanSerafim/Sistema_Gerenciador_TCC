package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede
        .controladores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .requisicoes.GerarGrupoTccRequisicao;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso
        .GerarGrupoTccCaso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grupo-tcc/api")
@CrossOrigin(origins = "*")
public class GrupoTccControlador {
    private final GerarGrupoTccCaso gerarGrupoTccCaso;

    public GrupoTccControlador(GerarGrupoTccCaso gerarGrupoTccCaso) {
        this.gerarGrupoTccCaso = gerarGrupoTccCaso;
    }

    @PostMapping("/gerar")
    @PreAuthorize("hasRole('ROLE_ALUNO')")
    public ResponseEntity<String> gerar(
            @RequestBody GerarGrupoTccRequisicao requisicao
    ) {
        var entrada = new GerarGrupoTccCaso.Entrada(
                requisicao.tema(),
                requisicao.tipoTcc(),
                requisicao.disciplina(),
                requisicao.matriculasAluno(),
                requisicao.idTurma()
        );

        gerarGrupoTccCaso.executar(entrada);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}