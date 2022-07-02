package com.manager.votemanager.controller;

import com.manager.votemanager.models.entity.Schedule;
import com.manager.votemanager.repository.ScheduleRepository;
import com.manager.votemanager.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, ScheduleRepository scheduleRepository) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Schedule>> getAll(){

        return ResponseEntity.ok(scheduleService.getAll());
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

}
