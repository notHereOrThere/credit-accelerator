package com.example.deal.service;

import com.example.credit.application.model.FinishRegistrationRequestDTO;
import com.example.credit.application.model.LoanApplicationRequestDTO;
import com.example.credit.application.model.LoanOfferDTO;

import java.util.List;

public interface DealService {
    List<LoanOfferDTO> calculateLoanConditions(LoanApplicationRequestDTO loanApplicationRequestDTO);

    void chooseLoanOffer(LoanOfferDTO loanOfferDTO);

    void finishRegistrationAndCalculateLoan(Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequestDTO);
}
