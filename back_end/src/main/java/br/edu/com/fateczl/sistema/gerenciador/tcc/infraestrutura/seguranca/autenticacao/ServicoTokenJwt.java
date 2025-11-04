package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.seguranca.autenticacao;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class ServicoTokenJwt {
    private final Key chaveDeAssinatura;
    private final long tempoDeExpiracao;

    public ServicoTokenJwt(
            @Value("${jwt.secret}") String segredo,
            @Value("${jwt.expiration.ms") long tempoDeExpiracao
    ) {
        byte[] bytesChave = segredo.getBytes();
        this.chaveDeAssinatura = Keys.hmacShaKeyFor(bytesChave);
        this.tempoDeExpiracao = tempoDeExpiracao;
    }

    public String gerarToken(String emailUsuario) {
        Date agora = new Date();
        Date dataExpiracao = new Date(agora.getTime() + tempoDeExpiracao);

        return Jwts.builder()
                .setSubject(emailUsuario)
                .setIssuedAt(agora)
                .setExpiration(dataExpiracao)
                .signWith(chaveDeAssinatura, SignatureAlgorithm.HS512)
                .compact();
    }

    public String extrairEmailDoToken(String token) {
        return extrairClaim(token, Claims::getSubject);
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(chaveDeAssinatura)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    private <T> T extrairClaim(
            String token,
            Function<Claims, T> claimsResolver
    ) {
        final Claims claims = extrairTodosClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extrairTodosClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(chaveDeAssinatura)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}