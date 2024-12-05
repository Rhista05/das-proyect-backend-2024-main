package com.example.backend.Controller.Services;

import com.example.backend.Model.Interface.Email;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private EmailFactory emailFactory;
    
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
        this.emailFactory = new EmailFactory(mailSender); // Creamos la fábrica
    }

    public void sendPasswordResetEmail(String toEmail, String resetLink) {
        Email email = emailFactory.createEmail("passwordReset", toEmail, resetLink, null, null);
        email.send(toEmail);
    }

    public void sendPaymentConfirmationEmail(String toEmail, String clientName, String saleDetails) {
        Email email = emailFactory.createEmail("paymentConfirmation", toEmail, null, clientName, saleDetails);
        email.send(toEmail);
    }
}
