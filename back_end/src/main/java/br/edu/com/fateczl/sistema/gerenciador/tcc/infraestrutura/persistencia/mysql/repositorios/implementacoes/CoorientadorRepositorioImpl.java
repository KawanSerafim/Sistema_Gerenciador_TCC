package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.implementacoes;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia.mysql.mapeadores.CoorientadorMapeador;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia.mysql.modelos.CoorientadorModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia.mysql.repositorios.spring.CoorientadorRepositorioSpring;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades.Coorientador;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .CoorientadorRepositorio;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CoorientadorRepositorioImpl implements CoorientadorRepositorio {
    private final CoorientadorRepositorioSpring repositorioSpring;
    private final CoorientadorMapeador mapeador;

    public CoorientadorRepositorioImpl(
            CoorientadorRepositorioSpring repositorioSpring,
            CoorientadorMapeador mapeador
    ) {
        this.repositorioSpring = repositorioSpring;
        this.mapeador = mapeador;
    }

    @Override
    public Coorientador salvar(Coorientador coorientador) {
        var coorientadorModelo = mapeador.paraModelo(coorientador);
        var coorientadorSalvo = repositorioSpring.save(coorientadorModelo);

        return mapeador.paraDominio(coorientadorSalvo);
    }

    @Override
    public Optional<Coorientador> buscarPorNomeEOrigem(
            String nome,
            String origem
    ) {
        Optional<CoorientadorModelo> coorientadorModeloOpt = repositorioSpring
                .findByNomeAndOrigem(nome, origem);

        return coorientadorModeloOpt.map(mapeador::paraDominio);
    }
}