package com.manager.votemanager.service;

import com.manager.votemanager.dto.SessionRequestDto;
import com.manager.votemanager.dto.SessionStartRequestDto;
import com.manager.votemanager.models.entity.Schedule;
import com.manager.votemanager.models.entity.VotingSession;
import com.manager.votemanager.models.enums.StatusEnum;
import com.manager.votemanager.repository.ScheduleRepository;
import com.manager.votemanager.repository.VotingSessionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class VotingSessionService {

    private final VotingSessionRepository votingSessionrepository;

    private final ScheduleRepository scheduleRepository;

    public VotingSessionService(VotingSessionRepository votingSessionrepository, ScheduleRepository scheduleRepository) {
        this.votingSessionrepository = votingSessionrepository;
        this.scheduleRepository = scheduleRepository;
    }

    private boolean verifyExistentSchedule(Long scheduleId) {

        return votingSessionrepository.existsByScheduleId(scheduleId);
    }

    public List<VotingSession> getAll() {

        return votingSessionrepository.findAll();
    }


    public VotingSession createSession(SessionRequestDto dto) {
        if (verifyExistentSchedule(dto.getScheduleId())) {
            throw new RuntimeException("This Schedule has already been voted");
        }

        VotingSession votingSession = VotingSession.builder()
                .duration(dto.getDuration())
                .schedule(getSchedule(dto))
                .build();

        return votingSessionrepository.save(votingSession);
    }

    private Schedule getSchedule(SessionRequestDto dto) {
        return scheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    public VotingSession startSession(SessionStartRequestDto dto) {
        VotingSession votingSession = votingSessionrepository.findById(dto.getSessionId())
                .orElseThrow(() -> new RuntimeException("Voting Session not found!!!"));

        votingSession.setClosedAt(Instant.now().plus(closedTime(votingSession.getDuration()), ChronoUnit.SECONDS));

        return votingSessionrepository.save(votingSession);
    }

    @Scheduled(fixedDelay = 5000)
    public void closeSession() {

        List<VotingSession> votingSessionList = getAllExpiredVotingsButNotClosed();

        votingSessionList.forEach(voting -> {
            voting.getSchedule().setStatus(StatusEnum.valueOf("CLOSED"));
            votingSessionrepository.save(voting);
        });

    }

    private List<VotingSession> getAllExpiredVotingsButNotClosed() {

        return votingSessionrepository.findAll().stream().filter(
                voting -> Instant.now().isAfter(voting.getClosedAt())
                        && voting.getSchedule().getStatus().equals(StatusEnum.valueOf("OPEN"))
        ).collect(Collectors.toList());
    }

    private Integer closedTime(Integer duration) {

        return Objects.isNull(duration) ? 60 : duration;
    }
}
