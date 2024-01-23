package com.example.dossier.dto;

import com.example.dossier.dto.enums.Gender;
import com.example.dossier.dto.enums.MaritalStatus;
import com.example.dossier.dto.inner.Employment;
import com.example.dossier.dto.inner.Passport;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class Client {

    private Long clientId;
    private String lastName;
    private String firstName;
    private String middleName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
    private String email;
    private Gender gender;
    private MaritalStatus martialStatus;
    private Integer dependentAmount;
    private Passport passport;
    private Employment employment;
    private List<Application> applications = new java.util.ArrayList<>();

}