package com.example.deal.api;

import com.example.credit.application.api.DealApi;
import com.example.credit.application.model.FinishRegistrationRequestDTO;
import com.example.credit.application.model.LoanApplicationRequestDTO;
import com.example.credit.application.model.LoanOfferDTO;
import com.example.deal.dto.SesDto;
import com.example.deal.exception.UserException;
import com.example.deal.service.DealService;
import feign.FeignException;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Override
    public ResponseEntity<List<LoanOfferDTO>> calculateLoanConditions(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        List<LoanOfferDTO> loanOfferDTOs = dealService.calculateLoanConditions(loanApplicationRequestDTO);
        return ResponseEntity.ok(loanOfferDTOs);
    }

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

    @PostMapping("/deal/document/{applicationId}/send")
    @Operation(
            summary = "Запрос на отправку документов.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "500", description = "Error processing the request")
            }
    )
    public ResponseEntity<Void> sendDocuments(@RequestParam Long applicationId) {
        dealService.sendDocuments(applicationId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/deal/document/{applicationId}/sign")
    @Operation(
            summary = "Запрос на подписание документов.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "500", description = "Error processing the request")
            })
    public ResponseEntity<Void> signDocuments(@RequestParam Long applicationId) {
        dealService.signDocuments(applicationId);
        return ResponseEntity.noContent().build();

    }

    @PostMapping("/deal/document/{applicationId}/code")
    @Operation(
            summary = "Подписание документов.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "500", description = "Error processing the request")
            })
    public ResponseEntity<Void> codeDocuments(@RequestParam Long applicationId, @RequestBody SesDto sesDto) {
        dealService.codeDocuments(applicationId, sesDto);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка ввода: " + e.getMessage());
    }
    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleException2(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка ввода: " + e.getMessage());
    }

}
