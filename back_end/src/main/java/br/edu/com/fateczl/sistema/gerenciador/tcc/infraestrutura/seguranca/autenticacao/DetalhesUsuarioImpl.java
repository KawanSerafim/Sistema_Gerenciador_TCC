package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.seguranca.autenticacao;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.dominio.enums
        .StatusContaUsuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public record DetalhesUsuarioImpl(
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        StatusContaUsuario statusContaUsuario
) implements UserDetails {
    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities != null ? authorities : Collections.emptySet();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if(this.statusContaUsuario == null) {
            return false;
        }
        return this.statusContaUsuario == StatusContaUsuario.ATIVO;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if(this.statusContaUsuario == null) {
            return false;
        }
        return this.statusContaUsuario == StatusContaUsuario.ATIVO;
    }
}