package com.example.conveyor.service.prescoring;

import com.example.credit.application.model.*;


public class PreScoringValidator5 implements PreScoring {
    @Override
    public ValidatorResult preScore(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        String email = loanApplicationRequestDTO.getEmail();
        if (!checkEmail(email)) {
            return ValidatorResult.builder()
                    .codeErr(5)
                    .textErr("Email адрес - строка, подходящая под паттерн [\\w\\.]{2,50}@[\\w\\.]{2,20}")
                    .isValid(false)
                    .build();
        }
        return ValidatorResult.builder()
                .isValid(true)
                .build();    }

    private Boolean checkEmail(String str) {
        return str.matches("[\\w\\.]{2,50}@[\\w\\.]{2,20}");
    }
}
