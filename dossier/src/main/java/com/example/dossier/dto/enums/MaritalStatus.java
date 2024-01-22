package com.example.dossier.dto.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MaritalStatus {
    MARRIED("married"), SINGLE("single"), DIVORCE("divorce"), WIDOW_WIDOWER("widow_widower");

    @JsonValue
    private String value;

    MaritalStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
