package com.example.deal.entity.inner;

import com.example.deal.entity.Application;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "applied_offer", schema = "deal")
public class LoanOffer  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applied_offer_id")
    private Long appliedOfferId;

    @OneToOne(mappedBy = "appliedOffer")
    private Application application;

    @Column(name = "requested_amount")
    private BigDecimal requestedAmount;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "term")
    private Integer term;

    @Column(name = "monthly_payment")
    private BigDecimal monthlyPayment;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "is_insurance_enabled")
    private Boolean isInsuranceEnabled;

    @Column(name = "is_salary_client")
    private Boolean isSalaryClient;

}
