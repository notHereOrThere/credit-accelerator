package com.example.application.api;

import com.example.application.service.ApplicationService;
import com.example.credit.application.api.ApplicationApi;
import com.example.credit.application.model.LoanApplicationRequestDTO;
import com.example.credit.application.model.LoanOfferDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationController implements ApplicationApi {

    private final ApplicationService applicationService;

    @Override
    public ResponseEntity<List<LoanOfferDTO>> calculateLoanOffers(@RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {
        List<LoanOfferDTO> loanOfferDTOs = applicationService.calculateLoanOffers(loanApplicationRequestDTO);
        return ResponseEntity.ok(loanOfferDTOs);
    }

    @Override
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> chooseOffer( @RequestBody LoanOfferDTO loanOfferDTO) {
        applicationService.chooseOffer(loanOfferDTO);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка ввода: " + e.getMessage());
    }

}
