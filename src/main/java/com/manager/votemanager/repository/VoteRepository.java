package com.manager.votemanager.repository;

import com.manager.votemanager.models.entity.User;
import com.manager.votemanager.models.entity.Vote;
import com.manager.votemanager.models.entity.VotingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByUser(User user);

    Boolean existsByVotingSessionAndUser(VotingSession votingSession, User user);
}
