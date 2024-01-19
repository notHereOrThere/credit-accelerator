package com.example.deal.entity;

import com.example.deal.entity.enums.ApplicationStatus;
import com.example.deal.entity.inner.LoanOffer;
import com.example.deal.entity.inner.StatusHistory;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "application", schema = "deal")
@Getter
@Setter
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long applicationId;

    @ManyToOne()
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "creation_date")
    private Date creationDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "applied_offer_id")
    private LoanOffer appliedOffer;

    @Column(name = "sign_date")
    private Date signDate;

    @Column(name = "ses_code")
    private String sesCode;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<StatusHistory> statusHistory = new ArrayList<>();
}