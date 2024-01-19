package com.example.application.feign;

import com.example.credit.application.model.LoanApplicationRequestDTO;
import com.example.credit.application.model.LoanOfferDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "deal", url = "${deal.url}")
public interface DealFeignClient {

    @PostMapping( value = "/deal/application", produces = "application/json")
    List<LoanOfferDTO> calculateLoanOffers(@RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO);

    @PutMapping( value = "/deal/offer", produces = "application/json")
    void performLoanCalculation(@RequestBody LoanOfferDTO scoringDataDTO);
}
