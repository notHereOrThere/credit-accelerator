package com.example.dossier.mail;

import com.example.dossier.dto.Application;
import com.example.dossier.dto.EmailDto;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {

    void sendApplicationDenied(EmailDto event) throws MessagingException;

    void sendFinishRegistration(EmailDto event);

    void sendCreateDocuments(EmailDto event);

    void sendSendDocuments(Application event) throws MessagingException, IOException;

    void sendSendSes(EmailDto event);

    void sendCreditIssued(EmailDto event);
}
