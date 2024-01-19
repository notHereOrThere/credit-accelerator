package com.example.conveyor.service;



import com.example.credit.application.model.CreditDTO;
import com.example.credit.application.model.LoanApplicationRequestDTO;
import com.example.credit.application.model.LoanOfferDTO;
import com.example.credit.application.model.ScoringDataDTO;

import java.util.List;

public interface ConveyorService {

    List<LoanOfferDTO> offers(LoanApplicationRequestDTO loanApplicationRequestDTO);

    CreditDTO calculation(ScoringDataDTO scoringDataDTO);

}
