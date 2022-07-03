package com.manager.votemanager.service;

import com.manager.votemanager.dto.VoteRequestDto;
import com.manager.votemanager.models.entity.Vote;
import com.manager.votemanager.models.entity.VotingSession;
import com.manager.votemanager.repository.VoteRepository;
import com.manager.votemanager.repository.VotingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final VotingSessionRepository votingSessionRepository;
    private final VotingSessionService votingSessionService;

    @Autowired
    public VoteService(VoteRepository voteRepository, VotingSessionRepository votingSessionRepository, VotingSessionService votingSessionService) {
        this.voteRepository = voteRepository;
        this.votingSessionRepository = votingSessionRepository;
        this.votingSessionService = votingSessionService;
    }

    public Vote voting(VoteRequestDto dto) {
        Optional<VotingSession> votingSession = votingSessionRepository.findById(dto.getVoteSessionId());

        if (votingSession.isEmpty()) {
            throw new RuntimeException("Vote Session Not Found");
        }

        Vote vote = Vote.builder()
                .selectVote(dto.getVote())
                .voteInstant(Instant.now())
                .votingSession(votingSession.get()).build();

        verifyVoteSessionValidTime(vote);

        return voteRepository.save(vote);
    }

    private void verifyVoteSessionValidTime(Vote vote) {
        if (vote.getVoteInstant().isAfter(vote.getVotingSession().getClosedAt())) {
            throw new RuntimeException("Expired voting session");
        }
    }
}
