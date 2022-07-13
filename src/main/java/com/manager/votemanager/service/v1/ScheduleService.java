package com.manager.votemanager.service.v1;

import com.manager.votemanager.advice.NotFoundException;
import com.manager.votemanager.models.v1.dto.ScheduleRequestDto;
import com.manager.votemanager.models.v1.dto.ScheduleResponseDto;
import com.manager.votemanager.models.v1.entity.Schedule;
import com.manager.votemanager.models.v1.enums.StatusEnum;
import com.manager.votemanager.models.v1.enums.VoteEnum;
import com.manager.votemanager.repository.v1.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.util.Precision;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ScheduleService {

    private final ScheduleRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public ScheduleService(ScheduleRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public List<ScheduleResponseDto> getAll(){

        List<Schedule> list = repository.findAll();

        return list.stream().map(
                schedule -> modelMapper.map(schedule, ScheduleResponseDto.class)
        ).collect(Collectors.toList());
    }

    public ScheduleResponseDto getById(Long id){

        Schedule schedule = repository.findById(id).orElseThrow(()-> new NotFoundException("Schedule not found"));

        return modelMapper.map(schedule, ScheduleResponseDto.class);
    }

    public ScheduleResponseDto getByName(String name){

        Schedule schedule = repository.findByName(name).orElseThrow(()-> new NotFoundException("Schedule not found"));

        return modelMapper.map(schedule, ScheduleResponseDto.class);
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto){
        log.info("Creating Schedule: {}", scheduleRequestDto.getName());

        Schedule schedule = repository.save(buildSchedule(scheduleRequestDto));

        ScheduleResponseDto scheduleResponseDto = buildScheduleResponse(schedule);

        log.info("Create Schedule with name: {}", schedule.getName());
        return scheduleResponseDto;
    }

    public void changeStatus(Schedule source){

        source.setStatus(StatusEnum.CLOSED);

        log.info("Status of schedule with name {} has changed", source.getName());
        repository.save(source);
    }

    public void defineWinner(Schedule schedule){
        setPercent(schedule);

        if (schedule.getYesPercent() > schedule.getNoPercent()){

            log.info("In schedule {} YES win", schedule.getName());
            schedule.setWinner(VoteEnum.YES.getLabel());
        }else if(schedule.getYesPercent() < schedule.getNoPercent()){

            log.info("In schedule {} NO win", schedule.getName());
            schedule.setWinner(VoteEnum.NO.getLabel());
        }
        else{

            log.info("No winner, Draw result");
            schedule.setWinner("DRAW");
        }
        repository.save(schedule);
    }

    public void setPercent(Schedule schedule){

        if (schedule.getQtdVotes() != 0){
            schedule.setYesPercent(Precision.round(((
                    Double.valueOf(schedule.getQtdYes())/ schedule.getQtdVotes())*100), 2));
            schedule.setNoPercent(Precision.round(((
                    Double.valueOf(schedule.getQtdNo())/ schedule.getQtdVotes())*100), 2));
        }

    }

    private Schedule buildSchedule(ScheduleRequestDto scheduleRequestDto){
        return Schedule.builder()
                .name(scheduleRequestDto.getName())
                .description(scheduleRequestDto.getDescription())
                .status(StatusEnum.OPEN)
                .build();
    }

    private ScheduleResponseDto buildScheduleResponse(Schedule schedule){
        return ScheduleResponseDto.builder()
                .id(schedule.getId())
                .name(schedule.getName())
                .description(schedule.getDescription())
                .status(schedule.getStatus())
                .qtdVotes(schedule.getQtdVotes())
                .qtdYes(schedule.getQtdYes())
                .qtdNo(schedule.getQtdNo())
                .yesPercent(schedule.getYesPercent())
                .noPercent(schedule.getNoPercent()).build();
    }
}
