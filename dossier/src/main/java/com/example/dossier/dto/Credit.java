package com.example.dossier.dto;

import com.example.dossier.dto.enums.Status;
import com.example.dossier.dto.inner.PaymentSchedule;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Credit {

    private Long creditId;
    private BigDecimal amount;
    private List<Application> applications = new ArrayList<>();
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private BigDecimal psk;
    private List<PaymentSchedule> paymentSchedule = new ArrayList<>();
    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;
    private Status creditStatus;
}