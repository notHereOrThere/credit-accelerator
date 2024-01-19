package com.example.conveyor.model.tmp;

import com.example.conveyor.model.enums.Theme;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailMessage {
    String address;
    Long applicationId;
    Theme theme;
}