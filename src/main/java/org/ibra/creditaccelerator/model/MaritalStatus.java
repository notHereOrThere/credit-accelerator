package org.ibra.creditaccelerator.model;

public enum MaritalStatus {

    MARRIAGE("в браке"), NON_MARRIAGE("не в браке");

    public final String value;

    MaritalStatus(String value) {
        this.value = value;
    }
}
