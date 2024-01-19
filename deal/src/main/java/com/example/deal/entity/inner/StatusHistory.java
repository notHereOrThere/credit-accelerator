package com.example.deal.entity.inner;

import com.example.deal.entity.Application;
import com.example.deal.entity.enums.ChangeType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "status_history", schema = "deal")
public class StatusHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_history_id")
    private Long statusHistoryId;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @Column(name = "status")
    private String status;

    @Column(name = "created_time")
    @CreatedDate
    private Date time;

    @Column(name = "change_type")
    @Enumerated(EnumType.STRING)
    private ChangeType changeType;
}
