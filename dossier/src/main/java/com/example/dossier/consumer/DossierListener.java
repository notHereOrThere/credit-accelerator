package com.example.dossier.consumer;

import com.example.dossier.dto.Application;
import com.example.dossier.dto.EmailDto;
import com.example.dossier.feign.DossierFeignClient;
import com.example.dossier.mail.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DossierListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DossierListener.class);

    private final EmailService emailService;
    private final DossierFeignClient dossierFeignClient;

    @KafkaListener(topics = "${topic.application-denied}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void sendApplicationDenied(EmailDto event) throws MessagingException {
        LOGGER.info(String.format("Email event received in dossier service -> %s", event.toString()));

        emailService.sendApplicationDenied(event);
        LOGGER.info("Email event executed in dossier service");

    }

    @KafkaListener(topics = "${topic.finish-registration}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void sendFinishRegistration(EmailDto event) {
        LOGGER.info(String.format("Email event received in dossier service -> %s", event.toString()));

        emailService.sendFinishRegistration(event);

        LOGGER.info("Email event executed in dossier service");

    }

    @KafkaListener(topics = "${topic.create-documents}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void sendCreateDocuments(EmailDto event) {
        LOGGER.info(String.format("Email event received in dossier service -> %s", event.toString()));

        emailService.sendCreateDocuments(event);

        LOGGER.info("Email event executed in dossier service");

    }

    @KafkaListener(topics = "${topic.send-documents}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void sendSendDocuments(Application event) throws MessagingException, IOException {
        LOGGER.info(String.format("Application event received in dossier service -> %s", event.toString()));

        emailService.sendSendDocuments(event);

        dossierFeignClient.signDocuments(event.getApplicationId());

        LOGGER.info("Application event executed in dossier service");

    }

    @KafkaListener(topics = "${topic.send-ses}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void sendSendSes(EmailDto event) {
        LOGGER.info(String.format("Email event received in dossier service -> %s", event.toString()));

        emailService.sendSendSes(event);

        LOGGER.info("Email event executed in dossier service");

    }

    @KafkaListener(topics = "${topic.credit-issued}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void sendCreditIssued(EmailDto event) {
        LOGGER.info(String.format("Email event received in dossier service -> %s", event.toString()));

        emailService.sendCreditIssued(event);

        LOGGER.info("Email event executed in dossier service");

    }
}
