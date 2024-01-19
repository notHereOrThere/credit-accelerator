package com.example.deal.feign;

import com.example.credit.application.model.CreditDTO;
import com.example.credit.application.model.LoanApplicationRequestDTO;
import com.example.credit.application.model.LoanOfferDTO;
import com.example.credit.application.model.ScoringDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "deal", url = "${conveyer.url}")
public interface ConveyerFeignClient {

    @PostMapping( value = "/conveyor/offers", produces = "application/json")
    List<LoanOfferDTO> calculateLoanOffers(@RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO);

    @PostMapping( value = "/conveyor/calculation", produces = "application/json")
    CreditDTO performLoanCalculation(@RequestBody ScoringDataDTO scoringDataDTO);

}
