package com.example.dossier.dto.inner;

import com.example.dossier.dto.Application;
import com.example.dossier.dto.enums.ChangeType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class StatusHistory implements Serializable {
    private Long statusHistoryId;
    @JsonIgnore
    private Application application;
    private String status;
    private Date time;
    private ChangeType changeType;
}
