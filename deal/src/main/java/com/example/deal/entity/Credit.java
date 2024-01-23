package com.example.deal.entity;

import com.example.deal.entity.enums.Status;
import com.example.deal.entity.inner.PaymentSchedule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "credit", schema = "deal")
@Getter
@Setter
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credit_id")
    private Long creditId;

    @Column(name = "amount")
    private BigDecimal amount;

    @OneToMany(mappedBy = "credit", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Application> applications = new ArrayList<>();

    @Column(name = "term")
    private Integer term;

    @Column(name = "monthly_payment")
    private BigDecimal monthlyPayment;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "psk")
    private BigDecimal psk;


    @Column(name = "payment_schedule")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "credit")
    private List<PaymentSchedule> paymentSchedule = new ArrayList<>();

    @Column(name = "insurance_enable")
    private Boolean isInsuranceEnabled;

    @Column(name = "salary_client")
    private Boolean isSalaryClient;

    @Column(name = "credit_status")
    @Enumerated(value = EnumType.STRING)
    private Status creditStatus;
}