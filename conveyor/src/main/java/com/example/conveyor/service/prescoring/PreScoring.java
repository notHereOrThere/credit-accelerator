package com.example.conveyor.service.prescoring;

import com.example.credit.application.model.*;

public interface PreScoring {

    ValidatorResult preScore(LoanApplicationRequestDTO loanApplicationRequestDTO);
}
