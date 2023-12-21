package org.ibra.creditaccelerator.model;

public enum Gender {

    MALE("мужской"), FEMALE("женский");

    public final String value;

    Gender(String value) {
        this.value = value;
    }
}
