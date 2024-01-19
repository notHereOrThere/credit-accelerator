package com.example.conveyor.service.prescoring;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidatorResult {
    private int codeErr;
    private String textErr;
    private boolean isValid;
}
