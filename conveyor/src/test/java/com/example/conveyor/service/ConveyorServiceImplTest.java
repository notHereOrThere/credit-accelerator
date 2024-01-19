package com.example.conveyor.service;

import com.example.conveyor.model.exception.UserException;
import com.example.credit.application.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ConveyorServiceImplTest {

    @InjectMocks
    private ConveyorServiceImpl conveyorService;

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(conveyorService, "baseRate", "15");
    }

    @Test
    public void testOffersWithoutErrors() {
        LoanApplicationRequestDTO dto = new LoanApplicationRequestDTO();
        dto.setAmount(BigDecimal.valueOf(300000));
        dto.setTerm(15);
        dto.setFirstName("Ivan");
        dto.setLastName("Ivanov");
        dto.setMiddleName("Ivanovich");
        dto.setEmail("ivan@example.com");
        dto.setBirthdate(LocalDate.parse("2000-01-01"));
        dto.setPassportSeries("1234");
        dto.setPassportNumber("123456");



        List<LoanOfferDTO> result = conveyorService.offers(dto);

        assertEquals(22057.94, result.get(0).getMonthlyPayment().doubleValue());
        assertEquals(21917.16, result.get(1).getMonthlyPayment().doubleValue());
        assertEquals(22391.5,  result.get(2).getMonthlyPayment().doubleValue());
        assertEquals(22246.59, result.get(3).getMonthlyPayment().doubleValue());
    }

    @Test
    public void testOffersWithErrors() {
        LoanApplicationRequestDTO dto = new LoanApplicationRequestDTO();
        dto.setAmount(BigDecimal.valueOf(3000));
        dto.setTerm(5);
        dto.setFirstName("I");
        dto.setLastName("Ivanov");
        dto.setMiddleName("Ivanovich");
        dto.setEmail("ivanexample.com");
        dto.setBirthdate(LocalDate.parse("2020-01-01"));
        dto.setPassportSeries("12341");
        dto.setPassportNumber("123456");

        ReflectionTestUtils.setField(conveyorService, "baseRate", "15");
        assertThrows(UserException.class, () -> conveyorService.offers(dto));
    }

    @Test
    void testCalculationWithoutRefuse() {
        ScoringDataDTO dto = new ScoringDataDTO();

        dto.setAmount(BigDecimal.valueOf(300000));
        dto.setTerm(15);
        dto.setFirstName("Ivan");
        dto.setLastName("Ivanov");
        dto.setMiddleName("Ivanovich");
        dto.setBirthdate(LocalDate.parse("2000-01-01"));
        dto.setPassportSeries("1234");
        dto.setPassportNumber("123456");
        dto.setPassportIssueDate(LocalDate.parse("2022-01-31"));
        dto.setPassportIssueBranch("ОВД района Иваново");
        dto.setDependentAmount(2);
        dto.setAccount("408178xxxxxxxxxx1234");
        dto.setIsInsuranceEnabled(true);
        dto.setIsSalaryClient(true);
        dto.setMaritalStatus(ScoringDataDTO.MaritalStatusEnum.MARRIED);
        dto.setGender(ScoringDataDTO.GenderEnum.MALE);
        EmploymentDTO employmentDTO = new EmploymentDTO();
        employmentDTO.setEmployerINN("123456789");
        employmentDTO.setSalary(BigDecimal.valueOf(45000));
        employmentDTO.setWorkExperienceTotal(120);
        employmentDTO.setWorkExperienceCurrent(60);
        employmentDTO.setPosition(EmploymentDTO.PositionEnum.MIDDLE_MANAGER);
        employmentDTO.setEmploymentStatus(EmploymentDTO.EmploymentStatusEnum.EMPLOYED);
        dto.setEmployment(employmentDTO);

        CreditDTO creditDTO = conveyorService.calculation(dto);

        assertEquals(21497.88, creditDTO.getMonthlyPayment().doubleValue());
        assertEquals(322468.2, creditDTO.getPsk().doubleValue());
    }
}