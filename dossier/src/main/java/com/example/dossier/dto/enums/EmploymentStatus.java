package com.example.dossier.dto.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EmploymentStatus {
    UNEMPLOYED("unemployed"), SELF_EMPLOYED("self_unemployed"), EMPLOYED("employed"), BUSINESS_OWNER("business_owner");

    @JsonValue
    private String value;

    EmploymentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
