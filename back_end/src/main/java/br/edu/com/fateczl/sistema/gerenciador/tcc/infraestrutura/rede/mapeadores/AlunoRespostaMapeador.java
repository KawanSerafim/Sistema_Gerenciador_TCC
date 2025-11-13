package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede
        .mapeadores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.rede.dtos
        .respostas.AlunoResposta;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Aluno;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlunoRespostaMapeador {
    public AlunoResposta paraResposta(Aluno aluno) {
        return new AlunoResposta(
                aluno.getId(),
                aluno.getNome(),
                aluno.getMatricula(),
                aluno.getStatus()
        );
    }

    public List<AlunoResposta> paraListaResposta(List<Aluno> alunos) {
        return alunos.stream().map(this::paraResposta).toList();
    }
}