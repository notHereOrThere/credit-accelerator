package com.example.conveyor.service;

import com.example.conveyor.model.enums.EmploymentStatus;
import com.example.conveyor.model.enums.Gender;
import com.example.conveyor.model.enums.MaritalStatus;
import com.example.conveyor.model.enums.Position;
import com.example.conveyor.model.exception.UserException;
import com.example.credit.application.model.*;
import com.example.conveyor.service.prescoring.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConveyorServiceImpl implements ConveyorService {

    private final List<PreScoring> preScoring = new ArrayList<>();

    @Value("${base.rate}")
    private String baseRate;

    ConveyorServiceImpl() {
        preScoring.add(new PreScoringValidator1());
        preScoring.add(new PreScoringValidator2());
        preScoring.add(new PreScoringValidator3());
        preScoring.add(new PreScoringValidator4());
        preScoring.add(new PreScoringValidator5());
        preScoring.add(new PreScoringValidator6());
    }

    @Override
    public List<LoanOfferDTO> offers(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        List<ValidatorResult> results = new ArrayList<>();
        preScoring.forEach(e -> results.add(e.preScore(loanApplicationRequestDTO)));
        if (!results.stream().allMatch(ValidatorResult::isValid))
            throw new UserException("Прескоринг показал следующие ошибки: " +
                    results.stream().filter(e-> !e.isValid())
                            .map(ValidatorResult::toString).collect(Collectors.toList()));

        return generateOffers(loanApplicationRequestDTO).stream()
                .sorted((x,y) -> y.getRate().compareTo(x.getRate()))
                .collect(Collectors.toList());
    }

    @Override
    public CreditDTO calculation(ScoringDataDTO scoringDataDTO) {
        if (validateRefuse(scoringDataDTO)) {
            throw new UserException("Отказ в предоставлении кредита");
        }
        BigDecimal rate = calculateRate(scoringDataDTO);
//        на случай если используется страхование
//        BigDecimal totalAmount = calculateTotalAmount(scoringDataDTO.getAmount(),
//                                                      scoringDataDTO.getIsInsuranceEnabled(),
//                                                      scoringDataDTO.getTerm());

        BigDecimal monthlyPay = calculateMonthlyPayment(scoringDataDTO.getAmount(), rate, scoringDataDTO.getTerm());


        CreditDTO creditDTO = new CreditDTO();

        creditDTO.setAmount(scoringDataDTO.getAmount());
        creditDTO.setTerm(scoringDataDTO.getTerm());
        creditDTO.setMonthlyPayment(monthlyPay);
        creditDTO.setRate(rate);
        creditDTO.setPsk(calculatePSK(scoringDataDTO.getAmount(), scoringDataDTO.getTerm(), rate));
        creditDTO.setIsInsuranceEnabled(scoringDataDTO.getIsInsuranceEnabled());
        creditDTO.setIsSalaryClient(scoringDataDTO.getIsSalaryClient());

        creditDTO.setPaymentSchedule(generatePaymentSchedule(scoringDataDTO.getAmount(), rate, scoringDataDTO.getTerm(), monthlyPay));

        return creditDTO;
    }

    private BigDecimal calculatePSK(BigDecimal amount, Integer term, BigDecimal rate) {
        BigDecimal monthlyPayment = calculateMonthlyPayment(amount, rate, term);
        return monthlyPayment.multiply(BigDecimal.valueOf(term));
    }

    private List<LoanOfferDTO> generateOffers(LoanApplicationRequestDTO loanApplicationRequestDTO) {
        List<LoanOfferDTO> result = new ArrayList<>();
        {
            LoanOfferDTO loanOfferOne = new LoanOfferDTO();
            result.add(loanOfferOne);
            loanOfferOne.setRequestedAmount(loanApplicationRequestDTO.getAmount());
            loanOfferOne.setTerm(loanApplicationRequestDTO.getTerm());
            loanOfferOne.setIsInsuranceEnabled(false);
            loanOfferOne.setIsSalaryClient(false);
            loanOfferOne.setTotalAmount(calculateTotalAmount(loanOfferOne.getRequestedAmount(), loanOfferOne.getIsInsuranceEnabled(), loanOfferOne.getTerm()));
            loanOfferOne.setRate(calculateRate(loanOfferOne.getIsSalaryClient(), loanOfferOne.getIsInsuranceEnabled()));
            loanOfferOne.setMonthlyPayment(calculateMonthlyPayment(loanOfferOne.getTotalAmount(),
                    loanOfferOne.getRate(),
                    loanOfferOne.getTerm()));
        }
        {
            LoanOfferDTO loanOfferTwo = new LoanOfferDTO();
            result.add(loanOfferTwo);
            loanOfferTwo.setRequestedAmount(loanApplicationRequestDTO.getAmount());
            loanOfferTwo.setTerm(loanApplicationRequestDTO.getTerm());
            loanOfferTwo.setIsInsuranceEnabled(false);
            loanOfferTwo.setIsSalaryClient(true);
            loanOfferTwo.setTotalAmount(calculateTotalAmount(loanOfferTwo.getRequestedAmount(), loanOfferTwo.getIsInsuranceEnabled(), loanOfferTwo.getTerm()));
            loanOfferTwo.setRate(calculateRate(loanOfferTwo.getIsSalaryClient(), loanOfferTwo.getIsInsuranceEnabled()));
            loanOfferTwo.setMonthlyPayment(calculateMonthlyPayment(loanOfferTwo.getTotalAmount(),
                    loanOfferTwo.getRate(),
                    loanOfferTwo.getTerm()));
        }
        {
            LoanOfferDTO loanOfferThree = new LoanOfferDTO();
            result.add(loanOfferThree);
            loanOfferThree.setRequestedAmount(loanApplicationRequestDTO.getAmount());
            loanOfferThree.setTerm(loanApplicationRequestDTO.getTerm());
            loanOfferThree.setIsInsuranceEnabled(true);
            loanOfferThree.setIsSalaryClient(false);
            loanOfferThree.setTotalAmount(calculateTotalAmount(loanOfferThree.getRequestedAmount(), loanOfferThree.getIsInsuranceEnabled(), loanOfferThree.getTerm()));
            loanOfferThree.setRate(calculateRate(loanOfferThree.getIsSalaryClient(), loanOfferThree.getIsInsuranceEnabled()));
            loanOfferThree.setMonthlyPayment(calculateMonthlyPayment(loanOfferThree.getTotalAmount(),
                    loanOfferThree.getRate(),
                    loanOfferThree.getTerm()));
        }
        {
            LoanOfferDTO loanOfferFour = new LoanOfferDTO();
            result.add(loanOfferFour);
            loanOfferFour.setRequestedAmount(loanApplicationRequestDTO.getAmount());
            loanOfferFour.setTerm(loanApplicationRequestDTO.getTerm());
            loanOfferFour.setIsInsuranceEnabled(true);
            loanOfferFour.setIsSalaryClient(true);
            loanOfferFour.setTotalAmount(calculateTotalAmount(loanOfferFour.getRequestedAmount(), loanOfferFour.getIsInsuranceEnabled(), loanOfferFour.getTerm()));
            loanOfferFour.setRate(calculateRate(loanOfferFour.getIsSalaryClient(), loanOfferFour.getIsInsuranceEnabled()));
            loanOfferFour.setMonthlyPayment(calculateMonthlyPayment(loanOfferFour.getTotalAmount(),
                    loanOfferFour.getRate(),
                    loanOfferFour.getTerm()));
        }
        return result;
    }

    private BigDecimal calculateRate(boolean isSalaryClient, boolean isInsuranceEnabled) {
        BigDecimal res = BigDecimal.valueOf(Double.parseDouble(baseRate));
        if (isSalaryClient) {
            res = res.subtract(BigDecimal.ONE);
        }
        if (isInsuranceEnabled) {
            res = res.subtract(BigDecimal.valueOf(5));
        }
        return res;
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal totalAmount, BigDecimal rate, Integer term) {
        BigDecimal monthlyInterestRate = rate.divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_EVEN);

        BigDecimal numerator = monthlyInterestRate.multiply(monthlyInterestRate.add(BigDecimal.ONE).pow(term));
        BigDecimal denominator = (monthlyInterestRate.add(BigDecimal.ONE).pow(term)).subtract(BigDecimal.ONE);

        return numerator.divide(denominator, 32, RoundingMode.HALF_EVEN).multiply(totalAmount).setScale(2, RoundingMode.HALF_EVEN);
    }

    private BigDecimal calculateTotalAmount(BigDecimal requestedAmount, boolean isInsuranceEnabled, Integer term) {

        if (!isInsuranceEnabled)
            return requestedAmount;

        return requestedAmount.add(BigDecimal.valueOf(10000L)
                .add(requestedAmount.divide(BigDecimal.valueOf(1000), MathContext.DECIMAL128)
                        .multiply(BigDecimal.valueOf(term))));

    }

    private boolean validateRefuse(ScoringDataDTO scoringDataDTO) {
        EmploymentDTO employmentDTO = scoringDataDTO.getEmployment();
        if (employmentDTO.getEmploymentStatus()
                .equals(EmploymentStatus.UNEMPLOYED)) {
            return false;
        }

        BigDecimal amount = scoringDataDTO.getAmount();
        BigDecimal salary = employmentDTO.getSalary();
        if (amount.compareTo(salary.multiply(BigDecimal.valueOf(20))) < 0) {
            return false;
        }

        LocalDate birthDate = scoringDataDTO.getBirthdate();
        if (ChronoUnit.YEARS.between(birthDate, LocalDate.now()) < 20 ||
                ChronoUnit.YEARS.between(birthDate, LocalDate.now()) > 60) {
            return false;
        }

        if (employmentDTO.getWorkExperienceCurrent() < 3) {
            return false;
        }

        if (employmentDTO.getWorkExperienceTotal() < 12) {
            return false;
        }

        return true;
    }

    private BigDecimal calculateRate(ScoringDataDTO scoringDataDTO) {
        BigDecimal rate = BigDecimal.valueOf(Double.parseDouble(baseRate));
        EmploymentDTO employmentDTO = scoringDataDTO.getEmployment();
        if (employmentDTO.getEmploymentStatus().equals(EmploymentDTO.EmploymentStatusEnum.SELF_EMPLOYED)) {
            rate = rate.add(BigDecimal.valueOf(1));
        } else if (employmentDTO.getEmploymentStatus().equals(EmploymentDTO.EmploymentStatusEnum.BUSINESS_OWNER)) {
            rate = rate.add(BigDecimal.valueOf(3));
        }

        if (employmentDTO.getPosition().equals(EmploymentDTO.PositionEnum.MIDDLE_MANAGER)) {
            rate = rate.subtract(BigDecimal.valueOf(2));
        } else if (employmentDTO.getPosition().equals(EmploymentDTO.PositionEnum.TOP_MANAGER)) {
            rate = rate.subtract(BigDecimal.valueOf(4));
        }

        if (scoringDataDTO.getMaritalStatus().equals(ScoringDataDTO.MaritalStatusEnum.MARRIED)) {
            rate = rate.subtract(BigDecimal.valueOf(3));
        } else if (scoringDataDTO.getMaritalStatus().equals(ScoringDataDTO.MaritalStatusEnum.DIVORCE)) {
            rate = rate.add(BigDecimal.valueOf(1));
        }

        long diffYearBirthDateAndNow = ChronoUnit.YEARS.between(scoringDataDTO.getBirthdate(), LocalDate.now());
        if ((scoringDataDTO.getGender().equals(ScoringDataDTO.GenderEnum.FEMALE) &&
                (diffYearBirthDateAndNow >= 35 && diffYearBirthDateAndNow <= 60)) ||
                (scoringDataDTO.getGender().equals(ScoringDataDTO.GenderEnum.MALE) &&
                        (diffYearBirthDateAndNow >= 30 && diffYearBirthDateAndNow <= 55))
        ) {
            rate = rate.subtract(BigDecimal.valueOf(3));
        } else if (scoringDataDTO.getGender().equals(ScoringDataDTO.GenderEnum.NON_BINARY)) {
            rate = rate.add(BigDecimal.valueOf(3));
        }

        if (scoringDataDTO.getDependentAmount() > 1) {
            rate = rate.add(BigDecimal.valueOf(1));
        }

        return rate;
    }

    private static List<PaymentScheduleElement> generatePaymentSchedule(BigDecimal totalAmount, BigDecimal rate,
                                                                        Integer term, BigDecimal monthlyPayment) {
        BigDecimal monthlyInterestRate = rate.divide(BigDecimal.valueOf(12 * 100), 10, RoundingMode.HALF_EVEN);
        BigDecimal remainingDebt = totalAmount;
        List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

        for (int i = 1; i <= term; i++) {
            BigDecimal interestPayment = remainingDebt.multiply(monthlyInterestRate).setScale(2, RoundingMode.UP);
            BigDecimal debtPayment = monthlyPayment.subtract(interestPayment).setScale(2, RoundingMode.UP);
            remainingDebt = remainingDebt.subtract(debtPayment).setScale(2, RoundingMode.UP);

            PaymentScheduleElement element = new PaymentScheduleElement();
            element.setNumber(i);
            element.setDate(LocalDate.now().plusMonths(i));
            element.setTotalPayment(monthlyPayment);
            element.setInterestPayment(interestPayment);
            element.setDebtPayment(debtPayment);
            element.setRemainingDebt(remainingDebt);

            paymentSchedule.add(element);
        }

        return paymentSchedule;
    }
}