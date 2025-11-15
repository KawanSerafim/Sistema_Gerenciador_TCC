package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede
        .mapeadores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .requisicoes.AjusteTccRequisicao;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.casosdeuso
        .GerarCursoCaso;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AjusteTipoTccListaMapeador {
    public List<GerarCursoCaso.AjusteTccEntrada> paraEntrada(
            List<AjusteTccRequisicao> requisicao
    ) {
        return requisicao.stream()
                .map(ajusteReq -> new GerarCursoCaso.AjusteTccEntrada(
                        ajusteReq.tipoTcc(),
                        ajusteReq.maxAlunos()
                ))
                .collect(Collectors.toList());
    }
}