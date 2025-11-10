package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede
        .controladores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos.requisicoes.GerarProfessorRequisicao;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .respostas.ProfessorResposta;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso
        .CadastrarProfessorCaso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professores/api")
@CrossOrigin(origins = "*")
public class ProfessorControlador {
    private final CadastrarProfessorCaso cadastrarProfessorCaso;

    public ProfessorControlador(CadastrarProfessorCaso cadastrarProfessorCaso) {
        this.cadastrarProfessorCaso = cadastrarProfessorCaso;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ProfessorResposta> cadastrarProfessor(
            @RequestBody GerarProfessorRequisicao requisicao
    ) {
        var entrada = new CadastrarProfessorCaso.Entrada(
                requisicao.nome(),
                requisicao.matricula(),
                requisicao.email(),
                requisicao.senha(),
                requisicao.cargo()
        );

        var resultado = cadastrarProfessorCaso.executar(entrada);

        var corpoResposta = new ProfessorResposta(
                resultado.id(),
                resultado.nome(),
                resultado.matricula(),
                resultado.cargo()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(corpoResposta);
    }
}