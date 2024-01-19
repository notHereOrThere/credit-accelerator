package com.example.conveyor.service.prescoring;

import com.example.credit.application.model.*;

public class PreScoringValidator3 implements PreScoring {
    @Override
    public ValidatorResult preScore(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        Integer term = loanApplicationRequestDTO.getTerm();
        if (term < 6) {
            return ValidatorResult.builder()
                    .codeErr(3)
                    .textErr("Срок кредита - целое число, большее или равное 6")
                    .isValid(false)
                    .build();
        }
        return ValidatorResult.builder()
                .isValid(true)
                .build();    }
}
