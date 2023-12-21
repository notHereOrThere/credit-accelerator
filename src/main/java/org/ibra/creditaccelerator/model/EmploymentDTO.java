package org.ibra.creditaccelerator.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.PackagePrivate;

import java.math.BigDecimal;

@Getter
@Setter
@PackagePrivate
public class EmploymentDTO {

    String employerINN;
    BigDecimal salary;
    Integer workExperienceTotal;
    Integer workExperienceCurrent;
    EmploymentStatus employmentStatus;
    Position position;

    enum EmploymentStatus{
        EMPLOYMENT, UN_EMPLOYMENT
    }
    enum Position{
        SELF_EMPLOYMENT, WORKER
    }
}
