package com.example.dossier.consumer;

import com.example.dossier.dto.EmailDto;
import com.example.dossier.mail.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@RequiredArgsConstructor
public class DossierListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DossierListener.class);

    private final EmailService emailService;

    @KafkaListener(topics = "${topic.application-denied}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void sendApplicationDenied(EmailDto event) throws MessagingException {
        LOGGER.info(String.format("Order event received in stock service -> %s", event.toString()));

        emailService.sendApplicationDenied(event);

        LOGGER.info("Order event executed in stock service");

    }

    @KafkaListener(topics = "${topic.finish-registration}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void sendFinishRegistration(EmailDto event) {
        LOGGER.info(String.format("Order event received in stock service -> %s", event.toString()));

        emailService.sendFinishRegistration(event);

        LOGGER.info("Order event executed in stock service");

    }

    @KafkaListener(topics = "${topic.create-documents}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void sendCreateDocuments(EmailDto event) {
        LOGGER.info(String.format("Order event received in stock service -> %s", event.toString()));

        emailService.sendCreateDocuments(event);

        LOGGER.info("Order event executed in stock service");

    }

    @KafkaListener(topics = "${topic.send-documents}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void sendSendDocuments(EmailDto event) {
        LOGGER.info(String.format("Order event received in stock service -> %s", event.toString()));

        emailService.sendSendDocuments(event);

        LOGGER.info("Order event executed in stock service");

    }

    @KafkaListener(topics = "${topic.send-ses}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void sendSendSes(EmailDto event) {
        LOGGER.info(String.format("Order event received in stock service -> %s", event.toString()));

        emailService.sendSendSes(event);

        LOGGER.info("Order event executed in stock service");

    }

    @KafkaListener(topics = "${topic.credit-issued}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void sendCreditIssued(EmailDto event) {
        LOGGER.info(String.format("Order event received in stock service -> %s", event.toString()));

        emailService.sendCreditIssued(event);

        LOGGER.info("Order event executed in stock service");

    }
}
