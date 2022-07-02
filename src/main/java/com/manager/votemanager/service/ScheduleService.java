package com.manager.votemanager.service;

import com.manager.votemanager.models.entity.Schedule;
import com.manager.votemanager.models.enums.StatusEnum;
import com.manager.votemanager.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

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



}
