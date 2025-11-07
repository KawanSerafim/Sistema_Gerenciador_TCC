package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.implementacoes;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.mapeadores.ProfessorMapeador;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.spring.ProfessorRepositorioSpring;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Professor;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .ProfessorRepositorio;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProfessorRepositorioImpl implements ProfessorRepositorio {
    private final ProfessorRepositorioSpring repositorioSpring;
    private final ProfessorMapeador mapeador;

    public ProfessorRepositorioImpl(
            ProfessorRepositorioSpring repositorioSpring,
            ProfessorMapeador mapeador
    ) {
        this.repositorioSpring = repositorioSpring;
        this.mapeador = mapeador;
    }

    @Override
    public Professor salvar(Professor professor) {
        var professorModelo = mapeador.paraModelo(professor);
        repositorioSpring.save(professorModelo);

        return mapeador.paraDominio(professorModelo);
    }

    @Override
    public Optional<Professor> buscarPorMatricula(String matricula) {
        var professorModeloOpt = repositorioSpring.findByMatricula(matricula);

        return professorModeloOpt.map(this.mapeador::paraDominio);
    }

    @Override
    public Optional<Professor> buscarPorEmail(String email) {
        var professorModeloOpt = repositorioSpring
                .findByContaUsuarioEmail(email);

        return professorModeloOpt.map(this.mapeador::paraDominio);
    }
}