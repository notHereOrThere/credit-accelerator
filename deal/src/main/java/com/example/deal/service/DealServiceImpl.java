package com.example.deal.service;

import com.example.credit.application.model.*;
import com.example.deal.dto.EmailDto;
import com.example.deal.dto.SesDto;
import com.example.deal.entity.Application;
import com.example.deal.entity.Client;
import com.example.deal.entity.Credit;
import com.example.deal.entity.enums.ApplicationStatus;
import com.example.deal.entity.enums.ChangeType;
import com.example.deal.entity.enums.Status;
import com.example.deal.entity.inner.Employment;
import com.example.deal.entity.inner.LoanOffer;
import com.example.deal.entity.inner.StatusHistory;
import com.example.deal.exception.UserException;
import com.example.deal.feign.ConveyerFeignClient;
import com.example.deal.kafka.KafkaProducerService;
import com.example.deal.mapper.DealMapper;
import com.example.deal.repository.ApplicationRepository;
import com.example.deal.repository.ClientRepository;
import com.example.deal.repository.CreditRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    @Value("${topic.finish-registration}")
    private String finishRegistration;

    @Value("${topic.create-documents}")
    private String createDocument;

    @Value("${topic.send-documents}")
    private String sendDocuments;

    @Value("${topic.send-ses}")
    private String sendSes;

    @Value("${topic.credit-issued}")
    private String creditIssued;

    @Value("${topic.application-denied}")
    private String applicationDenied;


    private final ConveyerFeignClient feignClient;
    private final DealMapper dealMapper;
    private final ClientRepository clientRepository;
    private final CreditRepository creditRepository;
    private final ApplicationRepository applicationRepository;
    private final KafkaProducerService kafkaProducerService;


    @Override
    public List<LoanOfferDTO> calculateLoanConditions(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        Application application = new Application();
        application.setCreationDate(new Date());

        List<LoanOfferDTO> loanOfferDTOs = null;
        try {
            loanOfferDTOs = feignClient.calculateLoanOffers(loanApplicationRequestDTO);
        } catch (FeignException.BadRequest e) {
            EmailDto emailDto = new EmailDto();
            emailDto.setFio(loanApplicationRequestDTO.getLastName() + " " + loanApplicationRequestDTO.getFirstName());
            if (!StringUtils.isBlank(loanApplicationRequestDTO.getMiddleName())) {
                emailDto.setFio(emailDto.getFio() + " " +loanApplicationRequestDTO.getMiddleName());
            }
            emailDto.setEmail(loanApplicationRequestDTO.getEmail());
            emailDto.setEmailText("В ходе выполнения перскоринга выявлена ошибка: " + e.getMessage());
            kafkaProducerService.send(applicationDenied, emailDto);

            buildApplicationHistory(application, "Отказ");

            application.setStatus(ApplicationStatus.CC_DENIED);
            applicationRepository.save(application);
            throw new UserException(e.getMessage());
        } catch (FeignException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        Client client = dealMapper.loanApplicationRequestDtoToClientEntity(loanApplicationRequestDTO);

        clientRepository.save(client);

        application.setStatus(ApplicationStatus.PREAPPROVAL);
        application.setClient(client);

        Long applicationId = applicationRepository.save(application).getApplicationId();

        loanOfferDTOs.forEach(e -> e.setApplicationId(applicationId));

        return loanOfferDTOs;
    }

    @Override
    public void chooseLoanOffer(LoanOfferDTO loanOfferDTO) {
        Application application = applicationRepository.findById(loanOfferDTO.getApplicationId()).orElseThrow(EntityNotFoundException::new);

        buildApplicationHistory(application, "Предварительное подтверждение");

        LoanOffer loanOffer = dealMapper.loanOfferDtoToLoanOfferEntity(loanOfferDTO);
        application.setAppliedOffer(loanOffer);
        application.setStatus(ApplicationStatus.APPROVED);

        applicationRepository.save(application);

        EmailDto emailDto = new EmailDto();
        emailDto.setApplicationId(application.getApplicationId());

        emailDto.setFio(application.getClient().getLastName() + " " + application.getClient().getFirstName());
        if (!StringUtils.isBlank(application.getClient().getMiddleName())) {
            emailDto.setFio(emailDto.getFio() + " " + application.getClient().getMiddleName());
        }
        emailDto.setEmail(application.getClient().getEmail());
        emailDto.setEmailText("После завершения регистрации, мы сможем завершить подсчет условий кредита.");

        kafkaProducerService.send(finishRegistration, emailDto);

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

        CreditDTO creditDTO = null;
        try {
             creditDTO = feignClient.performLoanCalculation(scoringDataDTO);
        } catch (FeignException.BadRequest e) {
            application.setStatus(ApplicationStatus.CC_DENIED);
            buildApplicationHistory(application, "Скоринг не пройден");
            applicationRepository.save(application);

            EmailDto emailDto = new EmailDto();
            emailDto.setApplicationId(application.getApplicationId());
            emailDto.setFio(application.getClient().getLastName() + " " + application.getClient().getFirstName());
            if (!StringUtils.isBlank(application.getClient().getMiddleName())) {
                emailDto.setFio(emailDto.getFio() + " " + application.getClient().getMiddleName());
            }
            emailDto.setEmail(application.getClient().getEmail());
            emailDto.setEmailText("Произошла ошибка в ходе выполнения скоринга, данные некорректны.");
            kafkaProducerService.send(applicationDenied, emailDto);

            throw new UserException(e.getMessage());
        } catch (FeignException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        Credit credit = dealMapper.creditDtoToCreditEntity(creditDTO);
        credit.getPaymentSchedule().forEach(e -> e.setCredit(credit));
        credit.setCreditStatus(Status.CALCULATED);
        creditRepository.save(credit);

        application.setStatus(ApplicationStatus.CC_APPROVED);
        application.setCredit(credit);

        buildApplicationHistory(application, "Подтверждение");
        applicationRepository.save(application);

        EmailDto emailDto = new EmailDto();
        emailDto.setApplicationId(application.getApplicationId());
        emailDto.setFio(application.getClient().getLastName() + " " + application.getClient().getFirstName());
        if (!StringUtils.isBlank(application.getClient().getMiddleName())) {
            emailDto.setFio(emailDto.getFio() + " " + application.getClient().getMiddleName());
        }
        emailDto.setEmail(application.getClient().getEmail());
        emailDto.setEmailText("Кредит расчитан, вы можете получить выгрузку списка документов на свою почту.");
        kafkaProducerService.send(createDocument, emailDto);

    }

    @Override
    public void sendDocuments(Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(EntityNotFoundException::new);
        buildApplicationHistory(application, "Подготовка документов.");
        application.setStatus(ApplicationStatus.PREPARE_DOCUMENT);
        applicationRepository.save(application);
        kafkaProducerService.send(sendDocuments, application);
    }

    @Override
    public void signDocuments(Long applicationId) {
        SesDto sesDto = new SesDto();
        UUID uuid = UUID.randomUUID();

        Application application = applicationRepository.findById(applicationId).orElseThrow(EntityNotFoundException::new);
        application.setSesCode(uuid.toString());
        buildApplicationHistory(application, "Подготовка документов.");
        applicationRepository.save(application);

        sesDto.setSesCode(uuid.toString());

        EmailDto emailDto = new EmailDto();
        emailDto.setApplicationId(application.getApplicationId());
        emailDto.setFio(application.getClient().getLastName() + " " + application.getClient().getFirstName());
        if (!StringUtils.isBlank(application.getClient().getMiddleName())) {
            emailDto.setFio(emailDto.getFio() + " " + application.getClient().getMiddleName());
        }
        emailDto.setEmail(application.getClient().getEmail());
        emailDto.setEmailText("Никому не показывайте, ваш СЭС код - " + uuid);
        kafkaProducerService.send(sendSes, emailDto);

    }

    @Override
    public void codeDocuments(Long applicationId, SesDto sesDto) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(EntityNotFoundException::new);
        if (!application.getSesCode().equals(sesDto.getSesCode())) {
            EmailDto emailDto = new EmailDto();
            emailDto.setApplicationId(application.getApplicationId());
            emailDto.setFio(application.getClient().getLastName() + " " + application.getClient().getFirstName());
            if (!StringUtils.isBlank(application.getClient().getMiddleName())) {
                emailDto.setFio(emailDto.getFio() + " " + application.getClient().getMiddleName());
            }
            emailDto.setEmail(application.getClient().getEmail());
            emailDto.setEmailText("СЭС коды не совпадают.");
            kafkaProducerService.send(applicationDenied, emailDto);
            return;
        }

        application.setStatus(ApplicationStatus.CREDIT_ISSUED);
        buildApplicationHistory(application, "Кредит выдан");

        applicationRepository.save(application);

        EmailDto emailDto = new EmailDto();
        emailDto.setApplicationId(application.getApplicationId());
        emailDto.setFio(application.getClient().getLastName() + " " + application.getClient().getFirstName());
        if (!StringUtils.isBlank(application.getClient().getMiddleName())) {
            emailDto.setFio(emailDto.getFio() + " " + application.getClient().getMiddleName());
        }
        emailDto.setEmail(application.getClient().getEmail());
        emailDto.setEmailText("Кредит успешно выдан.");
        kafkaProducerService.send(creditIssued, emailDto);
    }

    @Override
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public Application getApplicationById(Long applicationId) {
        return applicationRepository.findById(applicationId).orElseThrow(EntityNotFoundException::new);
    }

    private void buildApplicationHistory(Application application, String text) {
        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setChangeType(ChangeType.AUTOMATIC);
        statusHistory.setTime(new Date());
        statusHistory.setStatus(text);
        statusHistory.setApplication(application);
        application.getStatusHistory().add(statusHistory);
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
