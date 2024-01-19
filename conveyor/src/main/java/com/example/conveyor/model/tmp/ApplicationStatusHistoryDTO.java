package com.example.conveyor.model.tmp;

import com.example.conveyor.model.enums.ChangeType;
import com.example.conveyor.model.enums.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationStatusHistoryDTO {
    LocalDateTime time;
    Status status;
    ChangeType changeType;
}