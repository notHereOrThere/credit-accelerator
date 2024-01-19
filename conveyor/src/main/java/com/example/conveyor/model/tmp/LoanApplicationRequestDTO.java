package com.example.conveyor.model.tmp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema
public class LoanApplicationRequestDTO {

    @Schema(description = "Запрашиваемая сумма", type = "string", example = "300000")
    BigDecimal amount;
    @Schema(description = "Количество расчетных периодов", type = "integer", example = "15")
    Integer term;
    @Schema(description = "Имя", type = "string", example = "Ivan")
    String firstName;
    @Schema(description = "Фамилия", type = "string", example = "Ivanov")
    String lastName;
    @Schema(description = "Отчество", type = "string", example = "Ivanovich")
    String middleName;
    @Schema(description = "Электронная почта", type = "string", example = "ivan@example.com")
    String email;
    @Schema(description = "Дата рождения", type = "string", example = "2000-01-01")
    LocalDate birthdate;
    @Schema(description = "Серия паспорта", type = "string", example = "1234")
    String passportSeries;
    @Schema(description = "Номер паспорта", type = "string", example = "123456")
    String passportNumber;
}