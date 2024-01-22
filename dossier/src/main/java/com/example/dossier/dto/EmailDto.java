package com.example.dossier.dto;

import lombok.Data;

@Data
public class EmailDto {

    private Long applicationId;
    private String email;
    private String fio;
    private String emailText;

}
