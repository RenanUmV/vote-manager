package com.manager.votemanager.service;

import com.manager.votemanager.advice.ClosedSessionException;
import com.manager.votemanager.advice.InvalidRequestExcpetion;
import com.manager.votemanager.advice.NotFoundException;
import com.manager.votemanager.constants.RabbitMQConstants;
import com.manager.votemanager.dto.ScheduleResponseDto;
import com.manager.votemanager.dto.SessionRequestDto;
import com.manager.votemanager.dto.SessionStartRequestDto;
import com.manager.votemanager.models.entity.Schedule;
import com.manager.votemanager.models.entity.VotingSession;
import com.manager.votemanager.models.enums.StatusEnum;
import com.manager.votemanager.models.enums.VoteEnum;
import com.manager.votemanager.repository.ScheduleRepository;
import com.manager.votemanager.repository.VotingSessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@Service
public class VotingSessionService {

    private final VotingSessionRepository votingSessionrepository;

    private final ScheduleRepository scheduleRepository;

    private final ScheduleService scheduleService;

    private final RabbitMQService rabbitMQService;


    public VotingSessionService(VotingSessionRepository votingSessionrepository, ScheduleRepository scheduleRepository,
                                ScheduleService scheduleService, RabbitMQService rabbitMQService) {
        this.votingSessionrepository = votingSessionrepository;
        this.scheduleRepository = scheduleRepository;
        this.scheduleService = scheduleService;
        this.rabbitMQService = rabbitMQService;
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
            throw new InvalidRequestExcpetion("The given Voting session has been created");
        }

        VotingSession votingSession = VotingSession.builder()
                .duration(closedTime(dto.getDuration()))
                .schedule(getSchedule(dto))
                .build();

        return votingSessionrepository.save(votingSession);
    }

    private Schedule getSchedule(SessionRequestDto dto) {
        return scheduleRepository.findById(dto.getScheduleId())
                .orElseThrow(() -> new NotFoundException("Schedule not found"));
    }

    public VotingSession startSession(SessionStartRequestDto dto) {
        VotingSession votingSession = votingSessionrepository.findById(dto.getSessionId())
                .orElseThrow(() -> new NotFoundException("Voting Session not found"));

        if(votingSession.getClosedAt() != null){

            throw new InvalidRequestExcpetion("This session has been started!");
        }

        if (votingSession.getSchedule().getStatus().equals(StatusEnum.valueOf("CLOSED"))){
            throw new ClosedSessionException("This Schedule is CLOSED");
        }

        votingSession.setClosedAt(Instant.now().plus(votingSession.getDuration(), ChronoUnit.SECONDS));

        log.info("Starting voting session for schedule: {}", votingSession.getSchedule().getName());
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
            scheduleService.defineWinner(voting.getSchedule());
            scheduleRepository.save(voting.getSchedule());
            ScheduleResponseDto dtoResponse = ScheduleResponseDto.convertToDto(voting.getSchedule());
            rabbitMQService.sendMessage(RabbitMQConstants.QUEUE_RESULT, dtoResponse);
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
