package com.example.deal.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailDto {

    private Long applicationId;
    private String email;
    private String fio;
    private String emailText;

}
