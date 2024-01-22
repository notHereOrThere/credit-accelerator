package com.example.dossier.mail;

import com.example.dossier.dto.EmailDto;

import javax.mail.MessagingException;

public interface EmailService {

    void sendApplicationDenied(EmailDto event) throws MessagingException;

    void sendFinishRegistration(EmailDto event);

    void sendCreateDocuments(EmailDto event);

    void sendSendDocuments(EmailDto event);

    void sendSendSes(EmailDto event);

    void sendCreditIssued(EmailDto event);
}
