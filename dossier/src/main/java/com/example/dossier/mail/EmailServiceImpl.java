package com.example.dossier.mail;

import com.example.dossier.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;

    @Override
    public void sendApplicationDenied(EmailDto event) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(event.getEmail());
        helper.setSubject("Отказ в выдаче кредита.");
        helper.setText(event.getEmailText());

        mailSender.send(message);
    }

    @Override
    public void sendFinishRegistration(EmailDto event) {

    }

    @Override
    public void sendCreateDocuments(EmailDto event) {

    }

    @Override
    public void sendSendDocuments(EmailDto event) {

    }

    @Override
    public void sendSendSes(EmailDto event) {

    }

    @Override
    public void sendCreditIssued(EmailDto event) {

    }

//    public void sendEmail(String to, String subject, String content) throws MessagingException {
//        String fileName = "example.txt";
//        String fileType = "text/plain";
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setTo(to);
//        helper.setSubject(subject);
//        helper.setText(content, true);
//
//        mailSender.send(message);
//    }
}