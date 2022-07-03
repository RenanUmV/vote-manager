package com.manager.votemanager.models.entity;

import com.manager.votemanager.models.entity.audit.DateAudit;
import com.manager.votemanager.models.enums.StatusEnum;
import com.manager.votemanager.models.enums.VoteEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
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
    private VoteEnum winner;

    @Column(name = "QTDVOTES")
    private Integer qtdVotes;

    @Column(name = "QTDYES")
    private Integer qtdYes;

    @Column(name = "QTDNO")
    private Integer qtdNo;

    @Column(name = "yesPercent")
    private Double yesPercent;

    @Column(name = "noPercent")
    private Double noPercent;

}
