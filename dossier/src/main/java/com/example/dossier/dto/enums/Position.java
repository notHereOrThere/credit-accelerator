package com.example.dossier.dto.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Position {
    WORKER("worker"), MIDDLE_MANAGER("middle_manager"), TOP_MANAGER("top_manager"), OWNER("owner");
    @JsonValue
    private String value;

    Position(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
