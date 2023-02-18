package ir.maktab.home_service.service.interf;

import org.springframework.mail.SimpleMailMessage;

public interface EmailSenderService {

    void sendEmail(SimpleMailMessage emailAddress);

    SimpleMailMessage createEmail(String toEmail, String confirmationToken, String personType);
}
