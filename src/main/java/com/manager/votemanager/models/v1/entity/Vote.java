package com.manager.votemanager.models.v1.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.manager.votemanager.models.v1.entity.audit.DateAudit;
import com.manager.votemanager.models.v1.enums.VoteEnum;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Data
@Builder
@Entity
@Table(name = "VOTE")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Vote extends DateAudit {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    @Column(name = "VOTE")
    private VoteEnum selectVote;

    @Column(name = "VOTEINSTANT")
    private Instant voteInstant;

    @ManyToOne
    @JsonIgnoreProperties("votes")
    @JoinColumn(name = "ID_VOTING_SESSION")
    private VotingSession votingSession;

    @ManyToOne
    @JsonIgnoreProperties("votesList")
    @JoinColumn(name = "fk_idUser")
    private User user;
}
