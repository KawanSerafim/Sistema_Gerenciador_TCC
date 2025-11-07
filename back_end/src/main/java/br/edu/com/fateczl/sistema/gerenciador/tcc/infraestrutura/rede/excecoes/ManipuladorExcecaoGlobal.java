package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.excecoes;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos.respostas.ErroDominioResposta;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos.respostas.ErroClienteResposta;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.excecoes
        .ExcecaoDominio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ManipuladorExcecaoGlobal {
    private static final Logger log = LoggerFactory
            .getLogger(ManipuladorExcecaoGlobal.class);

    @ExceptionHandler(ExcecaoDominio.class)
    public ResponseEntity<ErroDominioResposta> manipuladorExcecaoDominio(
            ExcecaoDominio excecaoDominio
    ) {
        log.error(excecaoDominio.getMessage());

        var erroResposta = new ErroDominioResposta(
                excecaoDominio.pegarCodigoErro(),
                "Ocorreu um erro no servidor!"
        );

        return ResponseEntity.badRequest().body(erroResposta);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroClienteResposta> manipuladorExcecaoJsonMalformado(
            HttpMessageNotReadableException excecao
    ) {
        log.error(excecao.getMessage());

        var erroResposta = new ErroClienteResposta(
                CodigoErro.CLIENTE_001_JSON_MALFORMADO,
                "Ocorreu um erro no servidor!"
        );

        return ResponseEntity.badRequest().body(erroResposta);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroClienteResposta> manipuladorGenericoExcecao(
            Exception excecao
    ) {
        log.error(excecao.getMessage());

        var erroResposta = new ErroClienteResposta(
                CodigoErro.CLIENTE_002_ERRO_INESPERADO,
                "Ocorreu um erro no servidor!"
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(erroResposta);
    }
}