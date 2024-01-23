package com.example.dossier.dto;

import com.example.dossier.dto.enums.ApplicationStatus;
import com.example.dossier.dto.inner.LoanOffer;
import com.example.dossier.dto.inner.StatusHistory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class Application {

    private Long applicationId;
    private Client client;
    private Credit credit;
    private ApplicationStatus status;
    private Date creationDate;
    private LoanOffer appliedOffer;
    private Date signDate;
    private String sesCode;
    private List<StatusHistory> statusHistory = new ArrayList<>();
}