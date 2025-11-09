package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.configuracoes
        .seguranca;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers
        .AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ConfiguracaoSeguranca {

    @Bean
    public SecurityFilterChain filtroChaveSeguranca(HttpSecurity http)
            throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(autorizacao -> autorizacao
                        .requestMatchers(
                                HttpMethod.POST,
                                "login/api"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/professores/api/cadastrar"
                        ).permitAll()

                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager gerenciadorAutenticacao(
            AuthenticationConfiguration configuracaoAutenticacao
    ) throws Exception {
        return configuracaoAutenticacao.getAuthenticationManager();
    }
}