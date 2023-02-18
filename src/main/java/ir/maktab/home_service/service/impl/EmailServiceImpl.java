package ir.maktab.home_service.service.impl;

import ir.maktab.home_service.exception.FailedToSendEmailException;
import ir.maktab.home_service.service.interf.EmailSender;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailSender {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    public void sendEmail(String to, String emailAddress) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(emailAddress, true);
            helper.setTo(to);
            helper.setSubject(" your emailAddress is Confirm");
            helper.setFrom("shabnamghorbani90@gmail.com");
            mailSender.send(mimeMessage);
        } catch (jakarta.mail.MessagingException e) {
            throw new FailedToSendEmailException("Failed to send emailAddress for: " + emailAddress);
        }
    }
}

