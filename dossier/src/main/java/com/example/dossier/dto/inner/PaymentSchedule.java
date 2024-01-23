package com.example.dossier.dto.inner;

import com.example.dossier.dto.Credit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@ToString
public class PaymentSchedule implements Serializable {
    private Long paymentScheduleId;
    @JsonIgnore
    private Credit credit;
    private Integer number;
    private LocalDate date;
    private BigDecimal totalPayment;
    private BigDecimal interestPayment;
    private BigDecimal debtPayment;
    private BigDecimal remainingDebt;
}
