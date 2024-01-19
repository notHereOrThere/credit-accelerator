package com.example.conveyor.model.tmp;

import com.example.conveyor.model.enums.Gender;
import com.example.conveyor.model.enums.MaritalStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FinishRegistrationRequestDTO {
    Integer dependentAmount;
    LocalDate passportIssueDate;
    String passportIssueBranch;
    EmploymentDTO employment;
    String account;
    MaritalStatus maritalStatus;
    Gender gender;
}