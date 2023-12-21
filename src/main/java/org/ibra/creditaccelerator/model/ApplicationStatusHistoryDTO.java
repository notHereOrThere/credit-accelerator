package org.ibra.creditaccelerator.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.PackagePrivate;

import java.time.LocalDate;

@Getter
@Setter
@PackagePrivate
public class ApplicationStatusHistoryDTO {

    Status status;
    MaritalStatus time;
    ChangeType changeType;



    enum Status{
        EMPLOYMENT, UN_EMPLOYMENT
    }
    enum ChangeType{
        FINISHED, ON_PROGRESS
    }
}
