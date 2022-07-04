package com.manager.votemanager.service;

import com.manager.votemanager.dto.SessionRequestDto;
import com.manager.votemanager.dto.SessionStartRequestDto;
import com.manager.votemanager.models.entity.Schedule;
import com.manager.votemanager.models.entity.VotingSession;
import com.manager.votemanager.models.enums.StatusEnum;
import com.manager.votemanager.models.enums.VoteEnum;
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

    private final ScheduleService scheduleService;

    public VotingSessionService(VotingSessionRepository votingSessionrepository, ScheduleRepository scheduleRepository,
                                ScheduleService scheduleService) {
        this.votingSessionrepository = votingSessionrepository;
        this.scheduleRepository = scheduleRepository;
        this.scheduleService = scheduleService;
    }

    private boolean verifyExistentSchedule(Long scheduleId) {

        return votingSessionrepository.existsByScheduleId(scheduleId);
    }

    public List<VotingSession> getAll() {

        return votingSessionrepository.findAll();
    }

    public VotingSession getById(Long id){

        return votingSessionrepository.findById(id).orElse(null);
    }

    public VotingSession createSession(SessionRequestDto dto) {
        if (verifyExistentSchedule(dto.getScheduleId())) {
            throw new RuntimeException("The given Schedule does not exists");
        }

        VotingSession votingSession = VotingSession.builder()
                .duration(closedTime(dto.getDuration()))
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

        if (votingSession.getSchedule().getStatus().equals(StatusEnum.valueOf("CLOSED"))){
            throw new RuntimeException("This Schedule is CLOSED");
        }

        votingSession.setClosedAt(Instant.now().plus(votingSession.getDuration(), ChronoUnit.SECONDS));

        return votingSessionrepository.save(votingSession);
    }

    @Scheduled(fixedDelay = 5000)
    public void closeSession() {

        List<VotingSession> votingSessionList = getAllExpiredVotingsButNotClosed();

        votingSessionList.forEach(voting -> {
            voting.getSchedule().setQtdVotes(voting.getVotes().size());
            voting.getSchedule().setQtdYes(qtYes(voting));
            voting.getSchedule().setQtdNo(qtNo(voting));
            votingSessionrepository.save(voting);
            scheduleService.changeStatus(voting.getSchedule());
            scheduleService.setPercent(voting.getSchedule());
            scheduleService.setWinner(voting.getSchedule());
            scheduleRepository.save(voting.getSchedule());
        });
    }

    public Integer qtYes(VotingSession votingSession){

        return Math.toIntExact(votingSession.getVotes().stream()
                .filter(c -> c.getSelectVote().equals(VoteEnum.valueOf("YES")))
                .count());
    }

    public Integer qtNo(VotingSession votingSession){

        return Math.toIntExact(votingSession.getVotes().stream()
                .filter(c -> c.getSelectVote().equals(VoteEnum.valueOf("NO")))
                .count());
    }

    private List<VotingSession> getAllExpiredVotingsButNotClosed() {

        return votingSessionrepository.findAll().stream().filter(
                votingSession -> votingSession.isExpired() && votingSession.isOpen()
        ).collect(Collectors.toList());
    }

    private Integer closedTime(Integer duration) {

        return Objects.isNull(duration) ? 60 : duration;
    }
}
