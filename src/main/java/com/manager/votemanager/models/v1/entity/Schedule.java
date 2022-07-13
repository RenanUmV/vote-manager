package com.manager.votemanager.models.v1.entity;

import com.manager.votemanager.models.v1.entity.audit.DateAudit;
import com.manager.votemanager.models.v1.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Builder
@Table(name = "SCHEDULE")
@NoArgsConstructor
@AllArgsConstructor
public class Schedule extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "NAME")
    @Size(min = 4, max = 50)
    private String name;

    @NotNull
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS")
    @Enumerated
    private StatusEnum status;

    @Column(name = "WINNER")
    private String winner;

    @Column(name = "QTDVOTES")
    private Integer qtdVotes = 0;

    @Column(name = "QTDYES")
    private Integer qtdYes = 0;

    @Column(name = "QTDNO")
    private Integer qtdNo = 0;

    @Column(name = "yesPercent")
    private Double yesPercent = 0.00;

    @Column(name = "noPercent")
    private Double noPercent = 0.00;
}
