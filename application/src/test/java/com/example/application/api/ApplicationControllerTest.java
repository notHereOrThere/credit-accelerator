package com.example.application.api;

import com.example.application.service.ApplicationService;
import com.example.credit.application.model.LoanApplicationRequestDTO;
import com.example.credit.application.model.LoanOfferDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ApplicationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ApplicationService applicationService;

    @InjectMocks
    private ApplicationController applicationController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(applicationController)
                .setControllerAdvice(new ExceptionController()) // если у вас есть ControllerAdvice
                .build();
    }

    @Test
    public void testCalculateLoanOffers() throws Exception {
        // Arrange
        LoanApplicationRequestDTO requestDTO = new LoanApplicationRequestDTO();
        List<LoanOfferDTO> loanOfferDTOs = Arrays.asList(new LoanOfferDTO());

        when(applicationService.calculateLoanOffers(requestDTO)).thenReturn(loanOfferDTOs);

        // Act & Assert
        mockMvc.perform(post("/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(loanOfferDTOs)));

        verify(applicationService).calculateLoanOffers(requestDTO);
    }

    @Test
    public void testChooseOffer() throws Exception {
        // Arrange
        LoanOfferDTO loanOfferDTO = new LoanOfferDTO();

        doNothing().when(applicationService).chooseOffer(loanOfferDTO);


        mockMvc.perform(put("/application/offer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loanOfferDTO)))
                .andExpect(status().isNoContent());

        verify(applicationService).chooseOffer(loanOfferDTO);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class ExceptionController {
        @ExceptionHandler(FeignException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ResponseEntity<String> handleException(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка ввода: " + e.getMessage());
        }
    }
}
