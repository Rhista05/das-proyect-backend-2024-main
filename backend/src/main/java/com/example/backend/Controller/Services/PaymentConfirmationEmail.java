package com.example.backend.Controller.Services;

import com.example.backend.Model.Interface.Email;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public class PaymentConfirmationEmail implements Email {

    private JavaMailSender mailSender;
    private String clientName;
    private String saleDetails;

    public PaymentConfirmationEmail(JavaMailSender mailSender, String clientName, String saleDetails) {
        this.mailSender = mailSender;
        this.clientName = clientName;
        this.saleDetails = saleDetails;
    }

    @Override
    public void send(String toEmail) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setFrom("no-reply@fakemail.com");
            helper.setTo(toEmail);
            helper.setSubject("Payment Confirmation");

            String htmlContent = "<html><body>"
                    + "<h1>Payment Confirmation</h1>"
                    + "<p>Dear " + clientName + ",</p>"
                    + "<p>Thank you for your payment! We're pleased to confirm your recent transaction. Here are the details:</p>"
                    + "<p>" + saleDetails + "</p>"
                    + "<p>If you have any questions, feel free to contact us at any time.</p>"
                    + "</body></html>";

            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
            System.out.println("Payment confirmation email sent successfully.");
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
