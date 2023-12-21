package org.ibra.creditaccelerator.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.PackagePrivate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@PackagePrivate
public class FinishRegistrationRequestDTO {

    Gender gender;
    MaritalStatus maritalStatus;
    Integer dependentAmount;
    LocalDate passportIssueDate;
    String passportIssueBranch;
    EmploymentDTO employment;
    String account;

}
