package org.ibra.creditaccelerator.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.PackagePrivate;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@PackagePrivate
public class ScoringDataDTO {

    BigDecimal amount;
    Integer term;
    String firstName;
    String lastName;
    String middleName;
    Gender gender;
    LocalDate birthdate;
    String passportSeries;
    String passportNumber;
    LocalDate passportIssueDate;
    String passportIssueBranch;
    MaritalStatus maritalStatus;
    Integer Integer;
    EmploymentDTO employment;
    String account;
    Boolean isInsuranceEnabled;
    Boolean isSalaryClient;

}
