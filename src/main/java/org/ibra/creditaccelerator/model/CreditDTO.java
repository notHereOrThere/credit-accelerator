package org.ibra.creditaccelerator.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.PackagePrivate;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@PackagePrivate
public class CreditDTO {

    BigDecimal amount;
    Integer term;
    BigDecimal monthlyPayment;
    BigDecimal rate;
    BigDecimal psk;
    Boolean isInsuranceEnabled;
    Boolean isSalaryClient;
    List<PaymentScheduleElement> paymentSchedule;

//    {
//        "amount": "BigDecimal",
//            "term": "Integer",
//            "monthlyPayment": "BigDecimal",
//            "rate": "BigDecimal",
//            "psk": "BigDecimal",
//            "isInsuranceEnabled": "Boolean",
//            "isSalaryClient": "Boolean",
//            "paymentSchedule": "List<PaymentScheduleElement>"
//    }
}
