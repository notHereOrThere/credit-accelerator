package com.example.conveyor.api;

import com.example.conveyor.model.exception.UserException;
import com.example.conveyor.service.ConveyorService;
import com.example.credit.application.api.ConveyorApi;
import com.example.credit.application.model.CreditDTO;
import com.example.credit.application.model.LoanApplicationRequestDTO;
import com.example.credit.application.model.LoanOfferDTO;
import com.example.credit.application.model.ScoringDataDTO;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@Api(value = "Api для работы МС conveyor")
@RequiredArgsConstructor
public class ConveyorController implements ConveyorApi {

    private final ConveyorService conveyorService;

    @Override
    public ResponseEntity<List<LoanOfferDTO>> calculateLoanOffers(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        List<LoanOfferDTO> loanOffers = conveyorService.offers(loanApplicationRequestDTO);
        return ResponseEntity.ok(loanOffers);
    }

    @Override
    public ResponseEntity<CreditDTO> performLoanCalculation(ScoringDataDTO scoringDataDTO) {
        CreditDTO creditDTO = conveyorService.calculation(scoringDataDTO);
        return ResponseEntity.ok(creditDTO);
    }

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка ввода: " + e.getMessage());
    }

}
