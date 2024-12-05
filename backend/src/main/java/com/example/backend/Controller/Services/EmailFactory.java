package com.example.backend.Controller.Services;

import com.example.backend.Model.Interface.Email;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailFactory {

    private JavaMailSender mailSender;

    public EmailFactory(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public Email createEmail(String emailType, String toEmail, String resetLink, String clientName, String saleDetails) {
        switch (emailType) {
            case "passwordReset":
                return new PasswordResetEmail(mailSender, resetLink);
            case "paymentConfirmation":
                return new PaymentConfirmationEmail(mailSender, clientName, saleDetails);
            default:
                throw new IllegalArgumentException("Unknown email type: " + emailType);
        }
    }
}
