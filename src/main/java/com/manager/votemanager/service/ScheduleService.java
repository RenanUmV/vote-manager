package com.manager.votemanager.service;

import com.manager.votemanager.models.entity.Schedule;
import com.manager.votemanager.models.enums.StatusEnum;
import com.manager.votemanager.models.enums.VoteEnum;
import com.manager.votemanager.repository.ScheduleRepository;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        return repository.findById(id).orElse(null);
    }

    public Schedule getByName(String name){

        return repository.findByName(name).orElse(null);
    }

    public Schedule createSchedule(Schedule source){
        source.setStatus(StatusEnum.OPEN);

        return repository.save(source);
    }

    public void deleteWithId(Long id){

        repository.deleteById(id);
    }

    public void changeStatus(Schedule source){

        source.setStatus(StatusEnum.CLOSED);

        repository.save(source);
    }

    public void setWinner(Schedule schedule){

        if (schedule.getQtdYes() > schedule.getQtdNo()){

            schedule.setWinner(VoteEnum.YES);
        } else if (schedule.getQtdYes() < schedule.getQtdNo()) {

            schedule.setWinner(VoteEnum.NO);
        }
    }

    public void setPercent(Schedule schedule){


        schedule.setYesPercent(Precision.round(((
                Double.valueOf(schedule.getQtdYes())/ schedule.getQtdVotes())*100), 2));
        schedule.setNoPercent(Precision.round(((
                Double.valueOf(schedule.getQtdNo())/ schedule.getQtdVotes())*100), 2));
    }
}
