package com.example.dossier.dto.inner;

import com.example.dossier.dto.Credit;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
public class PaymentSchedule implements Serializable {
    private Long paymentScheduleId;
    private Credit credit;
    private Integer number;
    private LocalDate date;
    private BigDecimal totalPayment;
    private BigDecimal interestPayment;
    private BigDecimal debtPayment;
    private BigDecimal remainingDebt;
}
