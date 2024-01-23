package com.example.gateway.api;

import com.example.credit.application.api.DealApi;
import com.example.credit.application.model.FinishRegistrationRequestDTO;
import com.example.credit.application.model.SesDto;
import com.example.gateway.feign.DealFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DealController implements DealApi {

    private final DealFeignClient dealFeignClient;

    @Override
    public ResponseEntity<Void> confirmSignDocuments(@PathVariable Long applicationId, @RequestBody SesDto sesDto) {
        dealFeignClient.confirmSignDocuments(applicationId, sesDto);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> finishRegistrationAndCalculateLoan( @PathVariable Long applicationId, @RequestBody FinishRegistrationRequestDTO finishRegistrationRequestDTO) {
        dealFeignClient.finishRegistrationAndCalculateLoan(applicationId, finishRegistrationRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> sendDocuments(@PathVariable Long applicationId) {
        dealFeignClient.sendDocuments(applicationId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> signDocuments(@PathVariable Long applicationId) {
        dealFeignClient.signDocuments(applicationId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка ввода: " + e.getMessage().substring(e.getMessage().indexOf("Ошибка")));
    }
}
