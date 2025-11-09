package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede
        .controladores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .requisicoes.ValidarCodigoRequisicao;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso
        .ValidarCodigoCaso;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("validar-codigo/api")
@CrossOrigin(origins = "*")
public class ValidarCodigoControlador {
    private final ValidarCodigoCaso validarCodigoCaso;

    public ValidarCodigoControlador(ValidarCodigoCaso validarCodigoCaso) {
        this.validarCodigoCaso = validarCodigoCaso;
    }

    @PostMapping
    public ResponseEntity<String> validarCodigo(
            @RequestBody ValidarCodigoRequisicao requisicao
    ){
        var entrada = new ValidarCodigoCaso.Entrada(requisicao.codigo());

        validarCodigoCaso.executar(entrada);

        return ResponseEntity.status(HttpStatus.OK).body(
                "Código validado com sucesso!"
        );
    }
}