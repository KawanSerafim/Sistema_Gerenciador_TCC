package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.implementacoes;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.mapeadores.CursoMapeador;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.mapeadores.ProfessorMapeador;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.CursoModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.spring.CursoRepositorioSpring;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Curso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Professor;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .CursoRepositorio;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CursoRepositorioImpl implements CursoRepositorio {
    private final CursoRepositorioSpring repositorioSpring;
    private final CursoMapeador cursoMapeador;
    private final ProfessorMapeador professorMapeador;

    public CursoRepositorioImpl(
            CursoRepositorioSpring cursoRepositorioSpring,
            CursoMapeador cursoMapeador,
            ProfessorMapeador professorMapeador
    ) {
        this.repositorioSpring = cursoRepositorioSpring;
        this.cursoMapeador = cursoMapeador;
        this.professorMapeador = professorMapeador;
    }

    @Override
    public Curso salvar(Curso curso) {
        var cursoModelo = cursoMapeador.paraModelo(curso);
        var cursoSalvo = repositorioSpring.save(cursoModelo);

        return cursoMapeador.paraDominio(cursoSalvo);
    }

    @Override
    public Optional<Curso> buscarPorNome(String nome) {
        Optional<CursoModelo> cursoModeloOpt = repositorioSpring
                .findByNome(nome);

        return cursoModeloOpt.map(this.cursoMapeador::paraDominio);
    }

    @Override
    public Optional<Curso> buscarPorCoordenador(Professor coordenador) {
        var coordenadorModelo = professorMapeador.paraModelo(coordenador);

        Optional<CursoModelo> cursoModeloOpt = repositorioSpring
                .findByCoordenador(coordenadorModelo);

        return cursoModeloOpt.map(this.cursoMapeador::paraDominio);
    }
}