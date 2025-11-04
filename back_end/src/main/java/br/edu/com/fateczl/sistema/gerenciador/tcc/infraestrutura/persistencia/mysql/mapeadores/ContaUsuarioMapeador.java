package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia.mysql.mapeadores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.persistencia.mysql.modelos.ContaUsuarioModelo;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades.ContaUsuario;
import org.springframework.stereotype.Component;

@Component
public class ContaUsuarioMapeador {
    public ContaUsuarioModelo paraModelo(ContaUsuario dominio) {
        if(dominio == null) return null;

        var contaUsuarioModelo = new ContaUsuarioModelo();
        contaUsuarioModelo.setEmail(dominio.getEmail());
        contaUsuarioModelo.setSenha(dominio.getSenha());
        contaUsuarioModelo.setStatus(dominio.getStatus());

        return contaUsuarioModelo;
    }

    public ContaUsuario paraDominio(ContaUsuarioModelo modelo) {
        if(modelo == null) return null;

        var contaUsuario = new ContaUsuario();
        contaUsuario.setEmail(modelo.getEmail());
        contaUsuario.setSenha(modelo.getSenha());
        contaUsuario.setStatus(modelo.getStatus());

        return contaUsuario;
    }
}