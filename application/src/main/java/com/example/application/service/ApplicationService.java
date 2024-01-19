package com.example.application.service;

import com.example.credit.application.model.LoanApplicationRequestDTO;
import com.example.credit.application.model.LoanOfferDTO;

import java.util.List;

public interface ApplicationService {
    List<LoanOfferDTO> calculateLoanOffers(LoanApplicationRequestDTO loanApplicationRequestDTO);

    void chooseOffer(LoanOfferDTO loanOfferDTO);
}
