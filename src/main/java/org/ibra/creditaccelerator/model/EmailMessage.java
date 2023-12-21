package org.ibra.creditaccelerator.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.PackagePrivate;

@Getter
@Setter
@PackagePrivate
public class EmailMessage {

    String address;
    Theme theme;
    Long applicationId;

    enum Theme{
        EMPLOYMENT, UN_EMPLOYMENT
    }
}
