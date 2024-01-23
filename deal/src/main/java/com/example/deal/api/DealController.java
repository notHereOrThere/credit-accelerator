package com.example.deal.api;

import com.example.credit.application.api.DealApi;
import com.example.credit.application.model.FinishRegistrationRequestDTO;
import com.example.credit.application.model.LoanApplicationRequestDTO;
import com.example.credit.application.model.LoanOfferDTO;
import com.example.credit.application.model.SesDto;
import com.example.deal.exception.UserException;
import com.example.deal.service.DealService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Api для работы МС deal")
@RestController
@RequiredArgsConstructor
public class DealController implements DealApi {

    private final DealService dealService;

//    вызов из application
    @Override
    public ResponseEntity<List<LoanOfferDTO>> calculateLoanConditions(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        List<LoanOfferDTO> loanOfferDTOs = dealService.calculateLoanConditions(loanApplicationRequestDTO);
        return ResponseEntity.ok(loanOfferDTOs);
    }
//    вызов из application
    @Override
    public ResponseEntity<Void> chooseLoanOffer(LoanOfferDTO loanOfferDTO) {
        dealService.chooseLoanOffer(loanOfferDTO);
         return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> finishRegistrationAndCalculateLoan(Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequestDTO) {
        dealService.finishRegistrationAndCalculateLoan(applicationId, finishRegistrationRequestDTO);
         return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> sendDocuments(@PathVariable Long applicationId) {
        dealService.sendDocuments(applicationId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> signDocuments(@PathVariable Long applicationId) {
        dealService.signDocuments(applicationId);
        return ResponseEntity.noContent().build();

    }

    @Override
    public ResponseEntity<Void> confirmSignDocuments(@PathVariable Long applicationId, @RequestBody SesDto sesDto) {
        dealService.codeDocuments(applicationId, sesDto);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка ввода: " + e.getMessage().substring(e.getMessage().indexOf("Ошибка")));
    }

}
