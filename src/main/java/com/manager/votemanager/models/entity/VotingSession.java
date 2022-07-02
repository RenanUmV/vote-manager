package com.manager.votemanager.models.entity;

import com.manager.votemanager.models.entity.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "VOTINGSESSION")
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

    @OneToMany(mappedBy = "votingSession")
    private List<Vote> votes;



}

