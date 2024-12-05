package com.example.backend.Controller.Services;

import com.example.backend.Model.Interface.Email;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public class PasswordResetEmail implements Email {

    private JavaMailSender mailSender;
    private String resetLink;

    public PasswordResetEmail(JavaMailSender mailSender, String resetLink) {
        this.mailSender = mailSender;
        this.resetLink = resetLink;
    }

    @Override
    public void send(String toEmail) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setFrom("no-reply@fakemail.com");
            helper.setTo(toEmail);
            helper.setSubject("Password Reset Request");

            String htmlContent = "<html><body>"
                    + "<h1>Password Reset Request</h1>"
                    + "<p>To reset your password, click the following link:</p>"
                    + "<p><a href=\"" + resetLink + "\">Reset Password</a></p>"
                    + "<p>If you did not request this, please ignore this email.</p>"
                    + "</body></html>";

            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
            System.out.println("Password reset email sent successfully.");
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
