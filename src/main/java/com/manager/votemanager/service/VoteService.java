package com.manager.votemanager.service;

import com.manager.votemanager.dto.VoteRequestDto;
import com.manager.votemanager.models.entity.Vote;
import com.manager.votemanager.models.entity.VotingSession;
import com.manager.votemanager.models.enums.StatusEnum;
import com.manager.votemanager.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    private final VotingSessionService votingSessionService;

    @Autowired
    public VoteService(VoteRepository voteRepository, VotingSessionService votingSessionService) {
        this.voteRepository = voteRepository;
        this.votingSessionService = votingSessionService;
    }

    @Transactional
    public Vote voting(VoteRequestDto dto){
        VotingSession votingSession = votingSessionService.getById(dto.getVoteSessionId());

        Vote vote = Vote.builder()
                .vote(dto.getVote())
                .voteInstant(Instant.now())
                .votingSession(votingSession)
                .build();

        if (vote.getVoteInstant().isAfter(votingSession.getClosedAt())){
            throw  new RuntimeException("Expired voting session");
        }

        return voteRepository.save(vote);
    }
}
