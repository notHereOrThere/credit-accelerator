package com.example.deal.service;

import com.example.credit.application.model.*;
import com.example.deal.entity.Application;
import com.example.deal.entity.Client;
import com.example.deal.entity.Credit;
import com.example.deal.entity.enums.ChangeType;
import com.example.deal.entity.enums.Status;
import com.example.deal.entity.inner.Employment;
import com.example.deal.entity.inner.LoanOffer;
import com.example.deal.entity.inner.StatusHistory;
import com.example.deal.feign.ConveyerFeignClient;
import com.example.deal.mapper.DealMapper;
import com.example.deal.repository.ApplicationRepository;
import com.example.deal.repository.ClientRepository;
import com.example.deal.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final ConveyerFeignClient feignClient;
    private final DealMapper dealMapper;
    private final ClientRepository clientRepository;
    private final CreditRepository creditRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    public List<LoanOfferDTO> calculateLoanConditions(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        List<LoanOfferDTO> loanOfferDTOs = feignClient.calculateLoanOffers(loanApplicationRequestDTO);

        Client client = dealMapper.loanApplicationRequestDtoToClientEntity(loanApplicationRequestDTO);

        clientRepository.save(client);

        Application application = new Application();
        application.setCreationDate(new Date());
        application.setClient(client);

        Long applicationId = applicationRepository.save(application).getApplicationId();

        loanOfferDTOs.forEach(e -> e.setApplicationId(applicationId));

        return loanOfferDTOs;
    }

    @Override
    public void chooseLoanOffer(LoanOfferDTO loanOfferDTO) {
        Application application = applicationRepository.findById(loanOfferDTO.getApplicationId()).orElseThrow(EntityNotFoundException::new);

        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setTime(new Date());
        statusHistory.setChangeType(ChangeType.AUTOMATIC);
        statusHistory.setStatus("chose");

        LoanOffer loanOffer = dealMapper.loanOfferDtoToLoanOfferEntity(loanOfferDTO);
        application.setAppliedOffer(loanOffer);

        application.getStatusHistory().add(statusHistory);

        applicationRepository.save(application);
    }

    @Override
    public void finishRegistrationAndCalculateLoan(Long applicationId, FinishRegistrationRequestDTO finishRegistrationRequestDTO) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(EntityNotFoundException::new);

        Employment employment = dealMapper.employmentDtoToEmploymentEntity(finishRegistrationRequestDTO.getEmployment());
        Client client = application.getClient();
        client.setEmployment(employment);
        client.getPassport().setPassportIssueDate(finishRegistrationRequestDTO.getPassportIssueDate());
        client.getPassport().setPassportIssueBranch(finishRegistrationRequestDTO.getPassportIssueBranch());
        client.setDependentAmount(finishRegistrationRequestDTO.getDependentAmount());
        clientRepository.save(client);

        ScoringDataDTO scoringDataDTO = prepareScoringDataDto(finishRegistrationRequestDTO, application);

        CreditDTO creditDTO = feignClient.performLoanCalculation(scoringDataDTO);
        Credit credit = dealMapper.creditDtoToCreditEntity(creditDTO);
        credit.getPaymentSchedule().forEach(e -> e.setCredit(credit));
        credit.setCreditStatus(Status.CALCULATED);
        creditRepository.save(credit);

        application.setCredit(credit);
        applicationRepository.save(application);
    }

    private ScoringDataDTO prepareScoringDataDto(FinishRegistrationRequestDTO finishRegistrationRequestDTO, Application application) {
        ScoringDataDTO scoringDataDTO = new ScoringDataDTO();
        scoringDataDTO.setGender(mapGenderFRRDtoSDD(finishRegistrationRequestDTO.getGender()));
        scoringDataDTO.setMaritalStatus(mapMaritalStatusEnumFRRDtoSDD(finishRegistrationRequestDTO.getMaritalStatus()));
        scoringDataDTO.setDependentAmount(finishRegistrationRequestDTO.getDependentAmount());
        scoringDataDTO.setPassportIssueDate(finishRegistrationRequestDTO.getPassportIssueDate());
        scoringDataDTO.setPassportIssueBranch(finishRegistrationRequestDTO.getPassportIssueBranch());
        scoringDataDTO.setAccount(finishRegistrationRequestDTO.getAccount());
        scoringDataDTO.setEmployment(finishRegistrationRequestDTO.getEmployment());
        scoringDataDTO.setAmount(application.getAppliedOffer().getTotalAmount());
        scoringDataDTO.setTerm(application.getAppliedOffer().getTerm());
        scoringDataDTO.setFirstName(application.getClient().getFirstName());
        scoringDataDTO.setLastName(application.getClient().getLastName());
        scoringDataDTO.setMiddleName(application.getClient().getMiddleName());
        scoringDataDTO.setBirthdate(application.getClient().getBirthdate());
        scoringDataDTO.setPassportNumber(application.getClient().getPassport().getPassportNum());
        scoringDataDTO.setPassportSeries(application.getClient().getPassport().getPassportSer());
        scoringDataDTO.setIsInsuranceEnabled(application.getAppliedOffer().getIsInsuranceEnabled());
        scoringDataDTO.setIsSalaryClient(application.getAppliedOffer().getIsSalaryClient());

        return scoringDataDTO;
    }

    private ScoringDataDTO.GenderEnum mapGenderFRRDtoSDD(FinishRegistrationRequestDTO.GenderEnum genderEnum) {
        switch (genderEnum) {
            case NON_BINARY: return ScoringDataDTO.GenderEnum.NON_BINARY;
            case FEMALE: return ScoringDataDTO.GenderEnum.FEMALE;
            default: return ScoringDataDTO.GenderEnum.MALE;
        }
    }

    private ScoringDataDTO.MaritalStatusEnum mapMaritalStatusEnumFRRDtoSDD(FinishRegistrationRequestDTO.MaritalStatusEnum maritalStatusEnum) {
        switch (maritalStatusEnum) {
            case WIDOW_WIDOWER: return ScoringDataDTO.MaritalStatusEnum.WIDOW_WIDOWER;
            case SINGLE: return ScoringDataDTO.MaritalStatusEnum.SINGLE;
            case DIVORCE: return ScoringDataDTO.MaritalStatusEnum.DIVORCE;
            default: return ScoringDataDTO.MaritalStatusEnum.MARRIED;
        }
    }
}
