package com.example.conveyor.model.tmp;

import com.example.conveyor.model.enums.Gender;
import com.example.conveyor.model.enums.MaritalStatus;
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
public class ScoringDataDTO {
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
    @Schema(description = "Дата рождения", type = "string", example = "2000-01-01")
    LocalDate birthdate;
    @Schema(description = "Серия паспорта", type = "string", example = "1234")
    String passportSeries;
    @Schema(description = "Номер паспорта", type = "string", example = "123456")
    String passportNumber;
    @Schema(description = "Дата выдачи паспорта", type = "string", example = "2022-01-31")
    LocalDate passportIssueDate;
    @Schema(description = "Кем выдан паспорт ", type = "string", example = "ОВД района Иваново")
    String passportIssueBranch;
    @Schema(description = "Количество людей на попечении ", type = "integer", example = "2")
    Integer dependentAmount;
    EmploymentDTO employment;
    @Schema(description = "Банковский счет", type = "string", example = "408178xxxxxxxxxx1234")
    String account;
    @Schema(description = "Активность страховки", type = "boolean", example = "true")
    Boolean isInsuranceEnabled;
    @Schema(description = "Зарплатный клиент", type = "boolean", example = "true")
    Boolean isSalaryClient;
    @Schema(description = "Пол", type = "string", example = "male")
    Gender gender;
    @Schema(description = "Семейное положение", type = "string", example = "married")
    MaritalStatus maritalStatus;
}
