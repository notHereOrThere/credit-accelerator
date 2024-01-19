package com.example.deal.entity;

import com.example.deal.entity.enums.Gender;
import com.example.deal.entity.enums.MaritalStatus;
import com.example.deal.entity.inner.Employment;
import com.example.deal.entity.inner.Passport;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "client", schema = "deal")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date")
    private LocalDate birthdate;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = "martial_status")
    @Enumerated(value = EnumType.STRING)
    private MaritalStatus martialStatus;

    @Column(name = "dependent_amount")
    private Integer dependentAmount;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id")
    private Passport passport;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employment_id")
    private Employment employment;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications = new java.util.ArrayList<>();

}