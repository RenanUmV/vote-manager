package com.manager.votemanager.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.manager.votemanager.models.entity.audit.DateAudit;
import com.manager.votemanager.models.enums.VoteEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@Builder
@Entity
@Table(name = "VOTE")
@NoArgsConstructor
@AllArgsConstructor
public class Vote extends DateAudit {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    @Column(name = "VOTE")
    private VoteEnum vote;

    @Column(name = "VOTEINSTANT")
    private Instant voteInstant;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ID_VOTING_SESSION")
    private VotingSession votingSession;
}
