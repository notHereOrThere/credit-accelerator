package org.ibra.creditaccelerator.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.PackagePrivate;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@PackagePrivate
public class LoanApplicationRequestDTO {

    BigDecimal amount;
    Integer term;
    String firstName;
    String lastName;
    String middleName;
    String email;
    LocalDate birthdate;
    String passportSeries;
    String passportNumber;

}
