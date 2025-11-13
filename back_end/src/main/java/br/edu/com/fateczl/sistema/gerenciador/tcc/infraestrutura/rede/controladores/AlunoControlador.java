package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede
        .controladores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .requisicoes.FinalizarCadastroAlunoRequisicao;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso
        .FinalizarCadastroAlunoCaso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alunos/api")
@CrossOrigin(origins = "*")
public class AlunoControlador {
    private final FinalizarCadastroAlunoCaso finalizarCadastroAlunoCaso;

    public AlunoControlador(
            FinalizarCadastroAlunoCaso finalizarCadastroAlunoCaso
    ) {
        this.finalizarCadastroAlunoCaso = finalizarCadastroAlunoCaso;
    }

    @PostMapping("/finalizar-cadastro")
    public ResponseEntity<String> finalizarCadastro(
            @RequestBody FinalizarCadastroAlunoRequisicao requisicao
    ) {
        var entrada = new FinalizarCadastroAlunoCaso.Entrada(
                requisicao.matricula(),
                requisicao.email(),
                requisicao.senha()
        );

        finalizarCadastroAlunoCaso.executar(entrada);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}