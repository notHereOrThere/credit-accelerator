package com.example.deal.dto;

import lombok.Data;

@Data
public class EmailDto {

    private Long applicationId;
    private String email;
    private String fio;
    private String emailText;

}
