package com.system.customer_support_ticketing_system.services;

import com.system.customer_support_ticketing_system.exceptions.EmailSendingException;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom("your@gmail.com");

            mailSender.send(message);
        } catch (MailException e) {
            throw new EmailSendingException("Failed to send email to " + to);
        }
    }
}