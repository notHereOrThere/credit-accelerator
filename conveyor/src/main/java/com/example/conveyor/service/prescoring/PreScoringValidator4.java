package com.example.conveyor.service.prescoring;

import com.example.credit.application.model.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PreScoringValidator4 implements PreScoring {
    @Override
    public ValidatorResult preScore(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        LocalDate birthdate = loanApplicationRequestDTO.getBirthdate();
        if (ChronoUnit.YEARS.between(birthdate, LocalDate.now()) < 18) {
            return ValidatorResult.builder()
                    .codeErr(4)
                    .textErr("Дата рождения - число в формате гггг-мм-дд, не позднее 18 лет с текущего дня.")
                    .isValid(false)
                    .build();
        }
        return ValidatorResult.builder()
                .isValid(true)
                .build();    }
}
