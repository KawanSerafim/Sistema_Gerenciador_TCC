package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.configuracoes
        .semeador;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .Administrador;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.entidades
        .ContaUsuario;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .StatusContaUsuario;
import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas.repositorios
        .AdministradorRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SemeadorAdministrador implements CommandLineRunner {
    private static final String EMAIL_ADMIN = "admin@gerenciador.tcc.com";
    private static final String SENHA_ADMIN = "admin123";

    private final AdministradorRepositorio repositorio;
    private final PasswordEncoder criptografo;

    public SemeadorAdministrador(
            AdministradorRepositorio repositorio,
            PasswordEncoder criptografo
    ) {
        this.repositorio = repositorio;
        this.criptografo = criptografo;
    }

    @Override
    public void run(String... args) throws Exception {
        if(repositorio.buscarPorEmail(EMAIL_ADMIN).isPresent()) {
            return;
        }

        String senha = criptografo.encode(SENHA_ADMIN);

        var contaUsuario = new ContaUsuario();
        contaUsuario.setEmail(EMAIL_ADMIN);
        contaUsuario.setSenha(senha);
        contaUsuario.setStatus(StatusContaUsuario.ATIVO);

        var administrador = new Administrador(
                "Admin padrão",
                contaUsuario
        );

        repositorio.salvar(administrador);
    }
}