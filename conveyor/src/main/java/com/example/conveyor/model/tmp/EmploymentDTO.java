package com.example.conveyor.model.tmp;

import com.example.conveyor.model.enums.EmploymentStatus;
import com.example.conveyor.model.enums.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmploymentDTO {
    @Schema(description = "ИНН", type = "string", example = "123456789")
    String employerINN;
    @Schema(description = "Зарплата", type = "string", example = "45000")
    BigDecimal salary;
    @Schema(description = "Стаж (общий)", type = "string", example = "120")
    Integer workExperienceTotal;
    @Schema(description = "Стаж (текущий)", type = "string", example = "60")
    Integer workExperienceCurrent;
    @Schema(description = "Рабочий статус", type = "string", example = "employed")
    EmploymentStatus employmentStatus;
    @Schema(description = "Позиция", type = "string", example = "middle_manager")
    Position position;
}