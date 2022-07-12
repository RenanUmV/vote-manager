package com.manager.votemanager.repository.v1;

import com.manager.votemanager.models.v1.entity.VotingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VotingSessionRepository extends JpaRepository<VotingSession, Long> {

    Optional<VotingSession> findById(Long id);

    boolean existsByScheduleId(Long scheduleId);

}
