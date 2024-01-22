package com.example.dossier.dto.inner;

import com.example.dossier.dto.enums.EmploymentStatus;
import com.example.dossier.dto.enums.Position;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class Employment implements Serializable {
    private Long employmentId;
    private EmploymentStatus employmentStatus;
    private String employerINN;
    private BigDecimal salary;
    private Position position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}
