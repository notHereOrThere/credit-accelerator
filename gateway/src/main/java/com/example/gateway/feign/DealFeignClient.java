package com.example.gateway.feign;

import com.example.credit.application.model.FinishRegistrationRequestDTO;
import com.example.credit.application.model.LoanOfferDTO;
import com.example.credit.application.model.SesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "gateway-deal", url = "${deal.url}")
public interface DealFeignClient {

    @PostMapping( value = "/deal/document/{applicationId}/code", produces = "application/json")
    List<LoanOfferDTO> confirmSignDocuments(@PathVariable Long applicationId, @RequestBody SesDto sesDto);

    @PutMapping( value = "/deal/calculate/{applicationId}", produces = "application/json")
    void finishRegistrationAndCalculateLoan(@PathVariable Long applicationId, @RequestBody FinishRegistrationRequestDTO finishRegistrationRequestDTO);

    @PostMapping( value = "/deal/document/{applicationId}/send", produces = "application/json")
    void sendDocuments(@PathVariable Long applicationId);

    @PostMapping( value = "/deal/document/{applicationId}/sign", produces = "application/json")
    void signDocuments(@PathVariable Long applicationId);
}
