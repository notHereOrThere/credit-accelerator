package com.example.dossier.dto.inner;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter

public class Passport implements Serializable {

    private Long passportId;
    private String passportNum;
    private String passportSer;
    private LocalDate passportIssueDate;
    private String passportIssueBranch;

}
