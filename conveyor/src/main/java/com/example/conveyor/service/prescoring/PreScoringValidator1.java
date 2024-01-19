package com.example.conveyor.service.prescoring;

import com.example.credit.application.model.*;
import org.apache.commons.lang3.StringUtils;

public class PreScoringValidator1 implements PreScoring {
    @Override
    public ValidatorResult preScore(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        String firstName = loanApplicationRequestDTO.getFirstName();
        String lastName = loanApplicationRequestDTO.getLastName();
        String middleName = loanApplicationRequestDTO.getMiddleName();

        if (!checkName(firstName) || !checkName(lastName) || (StringUtils.isNotBlank(middleName) && !checkName(middleName))) {
            return ValidatorResult.builder()
                    .codeErr(1)
                    .textErr("Имя, Фамилия - от 2 до 30 латинских букв. Отчество, при наличии - от 2 до 30 латинских букв.")
                    .isValid(false)
                    .build();
        }
        return ValidatorResult.builder()
                .isValid(true)
                .build();
    }

    private Boolean checkName(String str) {
        return str.matches("[a-zA-Z]{2,30}");
    }
}
