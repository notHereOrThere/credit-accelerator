package com.example.deal.service;

import com.example.credit.application.model.*;
import com.example.deal.entity.Application;
import com.example.deal.entity.Client;
import com.example.deal.entity.Credit;
import com.example.deal.entity.inner.LoanOffer;
import com.example.deal.entity.inner.Passport;
import com.example.deal.feign.ConveyerFeignClient;
import com.example.deal.mapper.DealMapper;
import com.example.deal.repository.ApplicationRepository;
import com.example.deal.repository.ClientRepository;
import com.example.deal.repository.CreditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DealServiceImplTest {

    @Mock
    private ConveyerFeignClient feignClient;
    @Mock
    private DealMapper dealMapper;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private CreditRepository creditRepository;
    @Mock
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private DealServiceImpl dealService;

    @Test
    public void testCalculateLoanConditions() {

        LoanApplicationRequestDTO requestDTO = new LoanApplicationRequestDTO();
        List<LoanOfferDTO> mockLoanOffers = new ArrayList<>();
        Client mockClient = new Client();
        Application mockApplication = new Application();
        mockApplication.setApplicationId(1L);

        when(feignClient.calculateLoanOffers(requestDTO)).thenReturn(mockLoanOffers);
        when(dealMapper.loanApplicationRequestDtoToClientEntity(requestDTO)).thenReturn(mockClient);
        when(applicationRepository.save(any(Application.class))).thenReturn(mockApplication);

        List<LoanOfferDTO> result = dealService.calculateLoanConditions(requestDTO);

        assertNotNull(result);
        verify(feignClient).calculateLoanOffers(requestDTO);
        verify(dealMapper).loanApplicationRequestDtoToClientEntity(requestDTO);
        verify(clientRepository).save(mockClient);
        verify(applicationRepository).save(any(Application.class));
    }

    @Test
    public void testChooseLoanOffer() {
        LoanOfferDTO loanOfferDTO = new LoanOfferDTO();
        loanOfferDTO.setApplicationId(1L);
        Application mockApplication = new Application();
        LoanOffer mockLoanOffer = new LoanOffer();

        when(applicationRepository.findById(1L)).thenReturn(Optional.of(mockApplication));
        when(dealMapper.loanOfferDtoToLoanOfferEntity(loanOfferDTO)).thenReturn(mockLoanOffer);


        dealService.chooseLoanOffer(loanOfferDTO);

        verify(applicationRepository).findById(1L);
        verify(dealMapper).loanOfferDtoToLoanOfferEntity(loanOfferDTO);
        verify(applicationRepository).save(mockApplication);

    }


    @Test
    public void testFinishRegistrationAndCalculateLoanWithSpy() {

        Long applicationId = 1L;
        FinishRegistrationRequestDTO requestDTO = new FinishRegistrationRequestDTO();
        Application mockApplication = spy(new Application());
        Client mockClient = spy(new Client());
        mockClient.setPassport(new Passport());
        mockApplication.setClient(mockClient);
        mockApplication.setAppliedOffer(new LoanOffer());
        CreditDTO mockCreditDTO = new CreditDTO();
        Credit mockCredit = spy(new Credit());

        EmploymentDTO employmentDTO = new EmploymentDTO();
        employmentDTO.setEmploymentStatus(EmploymentDTO.EmploymentStatusEnum.EMPLOYED);
        employmentDTO.setPosition(EmploymentDTO.PositionEnum.MIDDLE_MANAGER);
        requestDTO.setEmployment(employmentDTO);
        requestDTO.setGender(FinishRegistrationRequestDTO.GenderEnum.MALE);
        requestDTO.setMaritalStatus(FinishRegistrationRequestDTO.MaritalStatusEnum.MARRIED);

        when(applicationRepository.findById(applicationId)).thenReturn(Optional.of(mockApplication));
        when(dealMapper.creditDtoToCreditEntity(mockCreditDTO)).thenReturn(mockCredit);
        when(feignClient.performLoanCalculation(any(ScoringDataDTO.class))).thenReturn(mockCreditDTO);


        dealService.finishRegistrationAndCalculateLoan(applicationId, requestDTO);


        verify(applicationRepository).findById(applicationId);
        verify(clientRepository).save(any(Client.class));
        verify(creditRepository).save(mockCredit);
        verify(applicationRepository).save(mockApplication);

    }


}