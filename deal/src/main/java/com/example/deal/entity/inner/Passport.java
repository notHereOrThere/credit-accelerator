package com.example.deal.entity.inner;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "passport", schema = "deal")
public class Passport implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passport_id")
    private Long passportId;

    @Column(name = "numbers")
    private String passportNum;

    @Column(name = "series")
    private String passportSer;

    @Column(name = "issue_date")
    private LocalDate passportIssueDate;

    @Column(name = "issue_branch")
    private String passportIssueBranch;

}
