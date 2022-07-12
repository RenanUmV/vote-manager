package com.manager.votemanager.repository.v1;

import com.manager.votemanager.models.v1.entity.User;
import com.manager.votemanager.models.v1.entity.Vote;
import com.manager.votemanager.models.v1.entity.VotingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByUser(User user);

    Boolean existsByVotingSessionAndUser(VotingSession votingSession, User user);
}
