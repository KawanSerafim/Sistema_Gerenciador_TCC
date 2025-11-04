package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.seguranca.autenticacao;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FiltroAutenticacaoJwt extends OncePerRequestFilter {
    private final ServicoTokenJwt servicoTokenJwt;
    private final UserDetailsService servicoDetalhesUsuario;
    private final Logger logger = LoggerFactory
            .getLogger(FiltroAutenticacaoJwt.class);

    public FiltroAutenticacaoJwt(
            ServicoTokenJwt servicoTokenJwt,
            UserDetailsService servicoDetalhesUsuario
    ) {
        this.servicoTokenJwt = servicoTokenJwt;
        this.servicoDetalhesUsuario = servicoDetalhesUsuario;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest requisicao,
            @NonNull HttpServletResponse resposta,
            @NonNull FilterChain chaveDeFiltro
    ) throws ServletException, IOException {
        final String cabecalhoAuth = requisicao.getHeader("Authorization");
        final String jwt;
        final String emailUsuario;

        if(cabecalhoAuth == null || !cabecalhoAuth.startsWith("Bearer ")) {
            chaveDeFiltro.doFilter(requisicao, resposta);
            return;
        }

        jwt = cabecalhoAuth.substring(7);

        try {
            emailUsuario = servicoTokenJwt.extrairEmailDoToken(jwt);
        } catch(Exception e) {
            logger.warn("Falha ao extrair email do Token: {}", e.getMessage());

            chaveDeFiltro.doFilter(requisicao, resposta);
            return;
        }

        if(emailUsuario != null
                && SecurityContextHolder.getContext()
                .getAuthentication() == null
        ) {
            UserDetails detalhesUsuario = this.servicoDetalhesUsuario
                    .loadUserByUsername(emailUsuario);

            if(servicoTokenJwt.validarToken(jwt)
                    && detalhesUsuario.isEnabled()
                    && detalhesUsuario.isAccountNonLocked()
            ) {
                var tokenAut = new UsernamePasswordAuthenticationToken(
                        detalhesUsuario,
                        null,
                        detalhesUsuario.getAuthorities()
                );

                tokenAut.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(requisicao)
                );

                SecurityContextHolder.getContext().setAuthentication(tokenAut);
            }
        }

        chaveDeFiltro.doFilter(requisicao, resposta);
    }
}