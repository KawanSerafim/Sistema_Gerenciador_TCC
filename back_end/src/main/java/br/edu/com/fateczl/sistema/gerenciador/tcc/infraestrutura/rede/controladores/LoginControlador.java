package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede
        .controladores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .requisicoes.LoginRequisicao;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .respostas.LoginResposta;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso.LoginCaso;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login/api")
@CrossOrigin(origins = "*")
public class LoginControlador {
    private final LoginCaso loginCaso;

    public LoginControlador(LoginCaso loginCaso) {
        this.loginCaso = loginCaso;
    }

    @PostMapping
    public ResponseEntity<LoginResposta> login(
            @RequestBody LoginRequisicao requisicao,
            HttpServletRequest httpRequisicao
    ) {
        var entrada = new LoginCaso.Entrada(
                requisicao.email(),
                requisicao.senha(),
                httpRequisicao.getRemoteAddr()
        );

        var resultado = loginCaso.executar(entrada);

        var corpoResposta = new LoginResposta(
                resultado.token(),
                resultado.tipoToken()
        );

        return ResponseEntity.status(HttpStatus.OK).body(corpoResposta);
    }
}