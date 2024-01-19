package com.example.deal.entity.inner;

import com.example.deal.entity.enums.EmploymentStatus;
import com.example.deal.entity.enums.Position;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "employment", schema = "deal")
public class Employment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employment_id")
    private Long employmentId;

    @Column(name = "employment_status")
    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus;

    @Column(name = "employer_inn")
    private String employerINN;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "work_position")
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(name = "work_experience_total")
    private Integer workExperienceTotal;

    @Column(name = "work_experience_current")
    private Integer workExperienceCurrent;
}
