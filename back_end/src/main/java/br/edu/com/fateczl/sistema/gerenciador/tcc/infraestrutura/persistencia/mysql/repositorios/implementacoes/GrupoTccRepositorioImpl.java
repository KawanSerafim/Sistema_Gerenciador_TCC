package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.implementacoes;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia.mysql.mapeadores.AlunoMapeador;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia.mysql.mapeadores.GrupoTccMapeador;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia.mysql.mapeadores.TurmaMapeador;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia.mysql.modelos.AlunoModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia.mysql.modelos.GrupoTccModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.spring.AlunoRepositorioSpring;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.spring.GrupoTccRepositorioSpring;
import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.repositorios.spring.TurmaRepositorioSpring;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades.Aluno;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades.GrupoTcc;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades.Turma;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .GrupoTccRepositorio;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class GrupoTccRepositorioImpl implements GrupoTccRepositorio {
    private final AlunoRepositorioSpring alunoRepositorioSpring;
    private final TurmaRepositorioSpring turmaRepositorioSpring;
    private final GrupoTccRepositorioSpring grupoTccRepositorioSpring;
    private final AlunoMapeador alunoMapeador;
    private final TurmaMapeador turmaMapeador;
    private final GrupoTccMapeador grupoTccMapeador;

    public GrupoTccRepositorioImpl(
            AlunoRepositorioSpring alunoRepositorioSpring,
            TurmaRepositorioSpring turmaRepositorioSpring,
            GrupoTccRepositorioSpring grupoTccRepositorioSpring,
            AlunoMapeador alunoMapeador,
            TurmaMapeador turmaMapeador,
            GrupoTccMapeador grupoTccMapeador
    ) {
        this.alunoRepositorioSpring = alunoRepositorioSpring;
        this.turmaRepositorioSpring = turmaRepositorioSpring;
        this.grupoTccRepositorioSpring = grupoTccRepositorioSpring;
        this.alunoMapeador = alunoMapeador;
        this.turmaMapeador = turmaMapeador;
        this.grupoTccMapeador = grupoTccMapeador;
    }

    @Override
    public GrupoTcc salvar(GrupoTcc grupoTcc) {
        var grupoTccModelo = grupoTccMapeador.paraModelo(grupoTcc);
        var grupoTccSalvo = grupoTccRepositorioSpring.save(grupoTccModelo);

        return grupoTccMapeador.paraDominio(grupoTccSalvo);
    }

    @Override
    public Optional<GrupoTcc> buscarPorAlunosETurma(
            List<Aluno> alunos,
            Turma turma
    ) {
        List<AlunoModelo> alunosModelo = alunos.stream()
                .map(alunoMapeador::paraModelo)
                .toList();

        var turmaModelo = turmaMapeador.paraModelo(turma);

        List<GrupoTccModelo> gruposComConflito = grupoTccRepositorioSpring
                .findByAlunosAndTurma(alunosModelo, turmaModelo);

        return gruposComConflito.stream()
                .findFirst()
                .map(grupoTccMapeador::paraDominio);
    }
}