package com.manager.votemanager.service;

import com.manager.votemanager.dto.VoteRequestDto;
import com.manager.votemanager.models.entity.User;
import com.manager.votemanager.models.entity.Vote;
import com.manager.votemanager.models.entity.VotingSession;
import com.manager.votemanager.repository.VoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final VotingSessionService votingSessionService;

    private final UserService userService;

    @Autowired
    public VoteService(VoteRepository voteRepository, VotingSessionService votingSessionService, UserService userService) {
        this.voteRepository = voteRepository;
        this.votingSessionService = votingSessionService;
        this.userService = userService;
    }

    public Vote voting(VoteRequestDto dto) {
        VotingSession votingSession = votingSessionService.getById(dto.getVoteSessionId());
        User user = userService.getByCpf(dto.getCpfUser());

        validUser(user);
        validSession(votingSession);

        Vote vote = Vote.builder()
                .selectVote(dto.getVote())
                .voteInstant(Instant.now())
                .user(user)
                .votingSession(votingSession).build();

        if (Boolean.TRUE.equals(hasVoted(votingSession, user))){

            log.info("Vote session Not Found");
            throw new RuntimeException("This user has already voted");
        }

        log.info("Invalid CPF");
        verifyVoteSessionValidTime(vote);

        return voteRepository.save(vote);
    }

    private void verifyVoteSessionValidTime(Vote vote) {
        if (vote.getVoteInstant().isAfter(vote.getVotingSession().getClosedAt())) {

            log.info("Expired voting session");
            throw new RuntimeException("Expired voting session");
        }
    }

    private Boolean hasVoted(VotingSession votingSession, User user){

        return voteRepository.existsByVotingSessionAndUser(votingSession, user);
    }

    private void validUser(User user){

        if (user == null){

            throw new RuntimeException("User not found!");
        }
    }

    private void validSession(VotingSession votingSession){

        if (votingSession == null){

            throw new RuntimeException("User not found!");
        }
    }
}
