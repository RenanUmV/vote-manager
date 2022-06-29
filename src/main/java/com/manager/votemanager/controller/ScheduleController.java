package com.manager.votemanager.controller;

import com.manager.votemanager.models.entity.Schedule;
import com.manager.votemanager.repository.ScheduleRepository;
import com.manager.votemanager.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, ScheduleRepository scheduleRepository) {
        this.scheduleService = scheduleService;
        this.scheduleRepository = scheduleRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getById(@PathVariable Long id){

        return new ResponseEntity<>(scheduleService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Schedule> getByName(String name){

        return new ResponseEntity<>(scheduleService.getByName(name), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Schedule> createSchedule(@Valid @RequestBody Schedule schedule){
        return new ResponseEntity<>(scheduleService.createSchedule(schedule), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){
        Optional<Schedule> del = scheduleRepository.findById(id);

        if(del.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        scheduleRepository.deleteById(id);
    }
}
