package com.example.conveyor.service.prescoring;

import com.example.credit.application.model.*;

import java.math.BigDecimal;

public class PreScoringValidator2 implements PreScoring {
    @Override
    public ValidatorResult preScore(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        BigDecimal amount = loanApplicationRequestDTO.getAmount();
        if (amount.compareTo(new BigDecimal("10000")) == -1) {
            return ValidatorResult.builder()
                    .codeErr(2)
                    .textErr("Сумма кредита - действительно число, большее или равное 10000.")
                    .isValid(false)
                    .build();
        }
        return ValidatorResult.builder()
                .isValid(true)
                .build();    }
}
