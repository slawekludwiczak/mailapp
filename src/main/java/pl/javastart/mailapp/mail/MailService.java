package pl.javastart.mailapp.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.javastart.mailapp.user.User;

@Service
public class MailService {
    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@mojaaplikacja.pl");
        message.setTo(user.getEmail());
        message.setSubject("Potwierdź rejestrację");
        String url = "http://localhost:8080/confirm?token=" + user.getToken();
        message.setText("""
                <html>
                <body>
                <h1>Potwierdź rejestrację</h1>
                <p>W celu potwierdzenia rejestracji kliknij poniższy link</p>
                <p><a href="%s">Potwierdzam</a></p>
                </body>
                </html>
                """.formatted(url));
        mailSender.send(message);
    }

    public void sendPasswordResetLink(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@mojaaplikacja.pl");
        message.setTo(user.getEmail());
        message.setSubject("Resetowanie hasła");
        String url = "http://localhost:8080/password-reset-form?token=" + user.getToken();
        message.setText("""
                <html>
                <body>
                <h1>Zmień hasło</h1>
                <p>W celu zmiany hasła kliknij poniższy link</p>
                <p><a href="%s">Zmień hasło</a></p>
                </body>
                </html>
                """.formatted(url));
        mailSender.send(message);
    }
}
