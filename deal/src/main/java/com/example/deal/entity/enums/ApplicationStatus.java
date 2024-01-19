package com.example.deal.entity.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ApplicationStatus {
    PREAPPROVAL("preapproval"), APPROVED("approved"), CC_DENIED("cc_denied"),
    CC_APPROVED("cc_approved"), PREPARE_DOCUMENT("prepare_document"), DOCUMENT_CREATED("document_created"),
    CLIENT_DENIED("client_denied"), DOCUMENT_SIGNED("document_signed"), CREDIT_ISSUED("credit_issued");

    @JsonValue
    private String value;

    ApplicationStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
