package com.example.conveyor.service.prescoring;

import com.example.credit.application.model.*;


public class PreScoringValidator6 implements PreScoring {
    @Override
    public ValidatorResult preScore(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        String ser = loanApplicationRequestDTO.getPassportSeries();
        String num = loanApplicationRequestDTO.getPassportNumber();


        String regexSer = "\\d{4}";
        String regexNum = "\\d{6}";

        if (!(ser.matches(regexSer) && num.matches(regexNum))) {
            return ValidatorResult.builder()
                    .codeErr(6)
                    .textErr("Серия паспорта - 4 цифры, номер паспорта - 6 цифр.")
                    .isValid(false)
                    .build();
        }
        return ValidatorResult.builder()
                .isValid(true)
                .build();
    }

}
