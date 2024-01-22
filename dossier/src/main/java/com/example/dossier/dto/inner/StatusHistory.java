package com.example.dossier.dto.inner;

import com.example.dossier.dto.Application;
import com.example.dossier.dto.enums.ChangeType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class StatusHistory implements Serializable {
    private Long statusHistoryId;
    private Application application;
    private String status;
    private Date time;
    private ChangeType changeType;
}
