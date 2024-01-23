package com.example.gateway.feign;

import com.example.credit.application.model.LoanApplicationRequestDTO;
import com.example.credit.application.model.LoanOfferDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "gateway-application", url = "${application.url}")
public interface ApplicationFeignClient {

    @PostMapping( value = "/application", produces = "application/json")
    List<LoanOfferDTO> calculateLoanOffers(@RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO);

    @PutMapping( value = "/application/offer", produces = "application/json")
    void chooseOffer(@RequestBody LoanOfferDTO scoringDataDTO);
}