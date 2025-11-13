package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.configuracoes
        .seguranca;

import br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.configuracoes
        .seguranca.autenticacao.FiltroAutenticacaoJwt;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication
        .configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration
        .EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers
        .AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication
        .UsernamePasswordAuthenticationFilter;

@Configuration
@EnableCaching
@EnableMethodSecurity
public class ConfiguracaoSeguranca {
    private final FiltroAutenticacaoJwt filtroJwt;

    public ConfiguracaoSeguranca(FiltroAutenticacaoJwt filtroJwt){
        this.filtroJwt = filtroJwt;
    }

    @Bean
    public SecurityFilterChain filtroChaveSeguranca(HttpSecurity http)
            throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(autorizacao -> autorizacao
                        .requestMatchers(
                                HttpMethod.POST,
                                "/login/api"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/professores/api/cadastrar"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/aluno/api/finalizar-cadastro"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/enviar-email/api/enviar"
                        ).permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/validar-codigo/api"
                        ).permitAll()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        filtroJwt,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager gerenciadorAutenticacao(
            AuthenticationConfiguration configuracaoAutenticacao
    ) throws Exception {
        return configuracaoAutenticacao.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}