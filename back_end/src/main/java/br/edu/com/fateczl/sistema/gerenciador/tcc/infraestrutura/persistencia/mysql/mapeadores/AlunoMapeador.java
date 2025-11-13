package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.mapeadores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia
        .mysql.modelos.AlunoModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Aluno;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AlunoMapeador {
    private final ContaUsuarioMapeador contaUsuarioMapeador;
    private final TurmaMapeador turmaMapeador;

    public AlunoMapeador(
            ContaUsuarioMapeador contaUsuarioMapeador,
            TurmaMapeador turmaMapeador
    ) {
        this.contaUsuarioMapeador = contaUsuarioMapeador;
        this.turmaMapeador = turmaMapeador;
    }

    public AlunoModelo paraModelo(Aluno dominio) {
        if(dominio == null) return null;

        var alunoModelo = new AlunoModelo();
        alunoModelo.setId(dominio.getId());
        alunoModelo.setNome(dominio.getNome());
        alunoModelo.setMatricula(dominio.getMatricula());
        alunoModelo.setContaUsuario(
                contaUsuarioMapeador.paraModelo(dominio.getContaUsuario())
        );
        alunoModelo.setStatus(dominio.getStatus());

        if(dominio.getTurmas() != null) {
            alunoModelo.setTurmas(
                    dominio.getTurmas().stream()
                            .map(turmaMapeador::paraModelo)
                            .collect(Collectors.toList())
            );
        }
        return alunoModelo;
    }

    public Aluno paraDominio(AlunoModelo modelo) {
        if(modelo == null) return null;

        var aluno = new Aluno();
        aluno.setId(modelo.getId());
        aluno.setNome(modelo.getNome());
        aluno.setMatricula(modelo.getMatricula());

        if(aluno.getContaUsuario() != null) {
            aluno.setContaUsuario(
                    contaUsuarioMapeador.paraDominio(modelo.getContaUsuario())
            );
        }
        aluno.setStatus(modelo.getStatus());

        if(modelo.getTurmas() != null) {
            aluno.setTurmas(
                    modelo.getTurmas().stream()
                            .map(turmaMapeador::paraDominio)
                            .collect(Collectors.toList())
            );
        }
        return aluno;
    }
}