package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.implementacoes;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.mapeadores.AlunoMapeador;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.AlunoModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.spring.AlunoRepositorioSpring;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Aluno;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .AlunoRepositorio;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AlunoRepositorioImpl implements AlunoRepositorio {
    private final AlunoRepositorioSpring repositorioSpring;
    private final AlunoMapeador mapeador;

    public AlunoRepositorioImpl(
            AlunoRepositorioSpring repositorioSpring,
            AlunoMapeador mapeador
    ) {
        this.repositorioSpring = repositorioSpring;
        this.mapeador = mapeador;
    }

    @Override
    public Aluno salvar(Aluno aluno) {
        var alunoModelo = mapeador.paraModelo(aluno);
        repositorioSpring.save(alunoModelo);

        return mapeador.paraDominio(alunoModelo);
    }

    @Override
    public Optional<Aluno> buscarPorMatricula(String matricula) {
        Optional<AlunoModelo> alunoModeloOpt = repositorioSpring
                .findByMatricula(matricula);

        return alunoModeloOpt.map(this.mapeador::paraDominio);
    }

    @Override
    public Optional<Aluno> buscarPorEmail(String email) {
        Optional<AlunoModelo> alunoModeloOpt = repositorioSpring
                .findByContaUsuarioEmail(email);

        return alunoModeloOpt.map(this.mapeador::paraDominio);
    }
}