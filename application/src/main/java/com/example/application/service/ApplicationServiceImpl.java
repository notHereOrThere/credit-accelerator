package com.example.application.service;

import com.example.application.feign.DealFeignClient;
import com.example.credit.application.model.LoanApplicationRequestDTO;
import com.example.credit.application.model.LoanOfferDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final DealFeignClient dealFeignClient;

    @Override
    public List<LoanOfferDTO> calculateLoanOffers(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        return dealFeignClient.calculateLoanOffers(loanApplicationRequestDTO);
    }

    @Override
    public void chooseOffer(LoanOfferDTO loanOfferDTO) {
        dealFeignClient.performLoanCalculation(loanOfferDTO);
    }
}
