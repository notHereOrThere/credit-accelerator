package org.ibra.creditaccelerator.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.PackagePrivate;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@PackagePrivate
public class PaymentScheduleElement {

    Integer number;
    LocalDate date;
    BigDecimal totalPayment;
    BigDecimal interestPayment;
    BigDecimal debtPayment;
    BigDecimal remainingDebt;

}
