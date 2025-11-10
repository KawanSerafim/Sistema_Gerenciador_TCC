package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.implementacoes;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.mapeadores.CursoMapeador;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.CursoModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.spring.CursoRepositorioSpring;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Curso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .CursoRepositorio;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CursoRepositorioImpl implements CursoRepositorio {
    private final CursoRepositorioSpring repositorioSpring;
    private final CursoMapeador mapeador;

    public CursoRepositorioImpl(
            CursoRepositorioSpring repositorioSpring,
            CursoMapeador mapeador
    ) {
        this.repositorioSpring = repositorioSpring;
        this.mapeador = mapeador;
    }

    @Override
    public Curso salvar(Curso curso) {
        var cursoModelo = mapeador.paraModelo(curso);
        repositorioSpring.save(cursoModelo);

        return mapeador.paraDominio(cursoModelo);
    }

    @Override
    public Optional<Curso> buscarNome(String nome) {
        Optional<CursoModelo> cursoModeloOpt = repositorioSpring
                .findByNome(nome);

        return cursoModeloOpt.map(this.mapeador::paraDominio);
    }
}