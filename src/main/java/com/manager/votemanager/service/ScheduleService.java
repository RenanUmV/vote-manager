package com.manager.votemanager.service;

import com.manager.votemanager.models.entity.Schedule;
import com.manager.votemanager.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    @Autowired
    private final ScheduleRepository repository;

    public ScheduleService(ScheduleRepository repository) {
        this.repository = repository;
    }

    public Schedule getById(Long id){

        return repository.findById(id).orElse(null);
    }

    public Schedule getByName(String name){

        return repository.findByName(name).orElse(null);
    }

    public Schedule createSchedule(Schedule schedule){

        return repository.save(schedule);
    }

    public void deleteWithId(Long id){

        repository.deleteById(id);
    }



}
