package com.example.dossier.dto.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Gender {
    MALE("male"), FEMALE("female"), NON_BINARY("non_binary");

    @JsonValue
    private String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
