package com.manager.votemanager.models.v1.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.manager.votemanager.models.v1.entity.audit.DateAudit;
import com.manager.votemanager.models.v1.enums.StatusEnum;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "VOTINGSESSION")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class VotingSession extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DURATION")
    private Integer duration;

    @Column(name = "CLOSEDAT")
    private Instant closedAt;

    @OneToOne
    @JoinColumn(name = "ID_PAUTA")
    private Schedule schedule;

    @JsonIgnoreProperties("votingSession")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "votingSession", cascade = CascadeType.ALL)
    private List<Vote> votes = new ArrayList<>();


    public boolean isExpired(){

        return this.getClosedAt() != null && this.getClosedAt().isBefore(Instant.now());
    }

    public boolean isOpen(){

        return getSchedule().getStatus().equals(StatusEnum.valueOf("OPEN"));
    }



}

