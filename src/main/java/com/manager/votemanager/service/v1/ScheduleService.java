package com.manager.votemanager.service.v1;

import com.manager.votemanager.advice.NotFoundException;
import com.manager.votemanager.models.v1.entity.Schedule;
import com.manager.votemanager.models.v1.enums.StatusEnum;
import com.manager.votemanager.models.v1.enums.VoteEnum;
import com.manager.votemanager.repository.v1.ScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ScheduleService {

    private final ScheduleRepository repository;
    @Autowired
    public ScheduleService(ScheduleRepository repository) {
        this.repository = repository;
    }

    public List<Schedule> getAll(){

        return repository.findAll();
    }

    public Schedule getById(Long id){

        return repository.findById(id).orElseThrow(()-> new NotFoundException("Schedule not found"));
    }

    public Schedule getByName(String name){

        return repository.findByName(name).orElse(null);
    }

    public Schedule createSchedule(Schedule source){
        source.setStatus(StatusEnum.OPEN);

        log.info("Create Schedule with name: {}", source.getName());
        return repository.save(source);
    }

    public void deleteWithId(Long id){

        repository.deleteById(id);
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
}
