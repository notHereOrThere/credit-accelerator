package com.example.deal.entity.inner;

import com.example.deal.entity.Credit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "payment_schedule", schema = "deal")
public class PaymentSchedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_schedule_id")
    private Long paymentScheduleId;

    @ManyToOne
    @JoinColumn(name = "credit_id")
    @JsonIgnore
    private Credit credit;

    @Column(name = "payment_number")
    private Integer number;

    @Column(name = "payment_date")
    private LocalDate date;

    @Column(name = "total_payment")
    private BigDecimal totalPayment;

    @Column(name = "interest_payment")
    private BigDecimal interestPayment;

    @Column(name = "debt_payment")
    private BigDecimal debtPayment;

    @Column(name = "remaining_debt")
    private BigDecimal remainingDebt;
}
