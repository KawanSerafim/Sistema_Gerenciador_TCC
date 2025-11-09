package br.edu.com.fateczl.sistema.gerenciador.tcc.infraestrutura.adaptadores;

import br.edu.com.fateczl.sistema.gerenciador.tcc.nucleo.portas
        .RemetenteEmailPorta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RemetenteEmailAdaptador implements RemetenteEmailPorta {
    private final JavaMailSender remetenteJavaMail;

    @Value("${spring.mail.from}")
    private String emailRemetente;

    public RemetenteEmailAdaptador(JavaMailSender remetenteJavaMail) {
        this.remetenteJavaMail = remetenteJavaMail;
    }

    @Override
    public void enviarEmail(String para, String assunto, String corpo) {
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom(emailRemetente);
        mensagem.setTo(para);
        mensagem.setSubject(assunto);
        mensagem.setText(corpo);

        remetenteJavaMail.send(mensagem);
    }
}