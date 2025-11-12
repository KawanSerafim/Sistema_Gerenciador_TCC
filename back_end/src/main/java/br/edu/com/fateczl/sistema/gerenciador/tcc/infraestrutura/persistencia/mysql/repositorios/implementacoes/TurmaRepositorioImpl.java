package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.implementacoes;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.mapeadores.CursoMapeador;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.mapeadores.TurmaMapeador;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.TurmaModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.spring.TurmaRepositorioSpring;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Curso;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Turma;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .Disciplina;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums.Turno;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .TurmaRepositorio;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TurmaRepositorioImpl implements TurmaRepositorio {
    private final TurmaRepositorioSpring repositorioSpring;
    private final TurmaMapeador turmaMapeador;
    private final CursoMapeador cursoMapeador;

    public TurmaRepositorioImpl(
            TurmaRepositorioSpring repositorioSpring,
            TurmaMapeador turmaMapeador,
            CursoMapeador cursoMapeador
    ) {
        this.repositorioSpring = repositorioSpring;
        this.turmaMapeador = turmaMapeador;
        this.cursoMapeador = cursoMapeador;
    }

    @Override
    public Turma salvar(Turma turma) {
        var turmaModelo = turmaMapeador.paraModelo(turma);
        repositorioSpring.save(turmaModelo);

        return turmaMapeador.paraDominio(turmaModelo);
    }

    @Override
    public Optional<Turma> buscarPorId(Long id) {
        Optional<TurmaModelo> turmaModeloOpt = repositorioSpring.findById(id);

        return turmaModeloOpt.map(turmaMapeador::paraDominio);
    }

    @Override
    public Optional<Turma> buscarPorCursoEDisciplinaETurnoEAnoESemestre(
            Curso curso,
            Disciplina disciplina,
            Turno turno,
            Integer ano,
            Integer semestre
    ) {
        var cursoModelo = cursoMapeador.paraModelo(curso);

        Optional<TurmaModelo> turmaModeloOpt = repositorioSpring
                .findByCursoAndDisciplinaAndTurnoAndAnoAndSemestre(
                        cursoModelo,
                        disciplina,
                        turno,
                        ano,
                        semestre
                );

        return turmaModeloOpt.map(turmaMapeador::paraDominio);
    }
}