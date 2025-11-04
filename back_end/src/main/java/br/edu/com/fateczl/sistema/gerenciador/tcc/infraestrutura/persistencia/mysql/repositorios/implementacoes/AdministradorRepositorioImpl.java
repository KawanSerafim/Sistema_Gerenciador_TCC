package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia.mysql.repositorios.implementacoes;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia.mysql.mapeadores.AdministradorMapeador;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia.mysql.modelos.AdministradorModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia.mysql.repositorios.spring.AdministradorRepositorioSpring;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades.Administrador;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios.AdministradorRepositorio;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AdministradorRepositorioImpl implements AdministradorRepositorio {
    private final AdministradorRepositorioSpring repositorioSpring;
    private final AdministradorMapeador mapeador;

    public AdministradorRepositorioImpl(
            AdministradorRepositorioSpring repositorioSpring,
            AdministradorMapeador mapeador
    ) {
        this.repositorioSpring = repositorioSpring;
        this.mapeador = mapeador;
    }

    @Override
    public Administrador salvar(Administrador administrador) {
        var administradorModelo = mapeador.paraModelo(administrador);
        repositorioSpring.save(administradorModelo);

        return mapeador.paraDominio(administradorModelo);
    }

    @Override
    public Optional<Administrador> buscarPorEmail(String email) {
        Optional<AdministradorModelo> administradorModeloOpt =
                repositorioSpring.findByContaUsuarioEmail(email);

        return administradorModeloOpt.map(
                this.mapeador::paraDominio
        );
    }
}