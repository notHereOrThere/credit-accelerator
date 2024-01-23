package com.example.dossier.mail;

import com.example.dossier.dto.Application;
import com.example.dossier.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;
    SimpleMailMessage mailMessage = new SimpleMailMessage();


    @Value("${spring.mail.username}")
    private String username;

    @Override
    public void sendApplicationDenied(EmailDto event) throws MessagingException {
        sendSimpleTextMessage(event, "Отказ в предоставлении кредита");
    }

    @Override
    public void sendFinishRegistration(EmailDto event) {
        sendSimpleTextMessage(event, "Завершение регистрации");
    }

    @Override
    public void sendCreateDocuments(EmailDto event) {
        sendSimpleTextMessage(event, "Выгрузка документов");
    }

    @Override
    public void sendSendSes(EmailDto event) {
        sendSimpleTextMessage(event, "СЭС код");
    }

    @Override
    public void sendCreditIssued(EmailDto event) {
        sendSimpleTextMessage(event, "Выдача кредита");
    }

    @Override
    public void sendSendDocuments(Application event) throws MessagingException, IOException {
        sendEmailWithAttachment(event);
    }

    private void sendEmailWithAttachment(Application event) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(event.getClient().getEmail());
        helper.setFrom(username);
        helper.setSubject("Документы по кредиту");

        // Текст сообщения
        helper.setText(event.toString());

        // Генерация файла
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();){

            outputStream.write(event.toString().getBytes(StandardCharsets.UTF_8));
            ByteArrayResource byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
// Добавление вложения
            helper.addAttachment("Заявка.txt", byteArrayResource);

            outputStream.write(event.getCredit().getPaymentSchedule().toString().getBytes(StandardCharsets.UTF_8));
            byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
            helper.addAttachment("График_платежей.txt", byteArrayResource);

            outputStream.write(buildCreditAct(event).getBytes(StandardCharsets.UTF_8));
            byteArrayResource = new ByteArrayResource(outputStream.toByteArray());
            helper.addAttachment("Кредитный_договор.txt", byteArrayResource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mailSender.send(message);
    }

    private String buildCreditAct(Application application) {
        try {
            ClassPathResource resource = new ClassPathResource("Credit_act_template.txt");
            String template = new BufferedReader(new InputStreamReader(
                    resource.getInputStream(), StandardCharsets.UTF_8))
                    .lines().collect(Collectors.joining("\n"));

            String fio = application.getClient().getLastName() + " " + application.getClient().getFirstName();

            return template.replace("{name}", fio)
                    .replace("{amount}", application.getCredit().getAmount().toString())
                    .replace("{term}", application.getCredit().getTerm().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sendSimpleTextMessage(EmailDto event, String subject) {
        mailMessage.setFrom(username);
        mailMessage.setTo(event.getEmail());
        mailMessage.setSubject(subject);

        StringBuilder str = new StringBuilder();
        str.append("Уважаемый ").append(event.getFio()).append("!\n")
                .append(event.getEmailText());

        mailMessage.setText(str.toString());

        mailSender.send(mailMessage);
    }
}