package br.edu.com.fateczl.sistema.gerenciador.tcc.aplicacao.ouvintes;

import br.edu.com.fateczl.sistema.gerenciador.tcc.aplicacao.eventos
        .UsuarioPedeEmailConfirmacaoEvento;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso
        .EnviarEmailConfirmacaoCaso;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EmailConfirmacaoOuvinte {
    private final EnviarEmailConfirmacaoCaso casoUso;

    public EmailConfirmacaoOuvinte(EnviarEmailConfirmacaoCaso casoUso) {
        this.casoUso = casoUso;
    }

    @EventListener
    public void lidarComUsuarioPedindoEmailConfirmacao(
            UsuarioPedeEmailConfirmacaoEvento evento
    ) {
        casoUso.executar(new EnviarEmailConfirmacaoCaso.Entrada(evento.email()));
    }
}