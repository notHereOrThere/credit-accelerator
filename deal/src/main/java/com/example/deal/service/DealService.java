package com.example.deal.service;

import com.example.credit.application.model.FinishRegistrationRequestDTO;
import com.example.credit.application.model.LoanApplicationRequestDTO;
import com.example.credit.application.model.LoanOfferDTO;
import com.example.deal.dto.SesDto;

import java.util.List;

public interface DealService {
    List<LoanOfferDTO> calculateLoanConditions(LoanApplicationRequestDTO loanApplicationRequestDTO);

    void chooseLoanOffer(LoanOfferDTO loanOfferDTO);

    void finishRegistrationAndCalculateLoan(Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequestDTO);

    void sendDocuments(Long applicationId);

    void signDocuments(Long applicationId);

    void codeDocuments(Long applicationId, SesDto sesDto);
}
