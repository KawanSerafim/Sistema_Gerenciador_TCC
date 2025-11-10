package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede
        .controladores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .requisicoes.EmailConfirmacaoRequisicao;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso
        .EnviarEmailConfirmacaoCaso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enviar-email/api")
@CrossOrigin(origins = "*")
public class EnviarEmailControlador {
    private final EnviarEmailConfirmacaoCaso enviarEmailCaso;

    public EnviarEmailControlador(EnviarEmailConfirmacaoCaso enviarEmailCaso) {
        this.enviarEmailCaso = enviarEmailCaso;
    }

    @PostMapping
    public ResponseEntity<String> enviar(
            @RequestBody EmailConfirmacaoRequisicao requisicao
    ){
        var entrada = new EnviarEmailConfirmacaoCaso.Entrada(requisicao.email());

        enviarEmailCaso.executar(entrada);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Email enviado com sucesso!");
    }
}