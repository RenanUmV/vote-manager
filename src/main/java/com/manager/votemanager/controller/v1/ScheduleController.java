package com.manager.votemanager.controller.v1;

import com.manager.votemanager.models.v1.dto.ScheduleRequestDto;
import com.manager.votemanager.models.v1.dto.ScheduleResponseDto;
import com.manager.votemanager.models.v1.entity.Schedule;
import com.manager.votemanager.repository.v1.ScheduleRepository;
import com.manager.votemanager.service.v1.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/schedule")
@Slf4j
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService, ScheduleRepository scheduleRepository) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ScheduleResponseDto>> getAll(){

        log.info("Find all schedules");
        return ResponseEntity.ok(scheduleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getById(@PathVariable Long id){

        log.info("Find by id: {}", id);
        return new ResponseEntity<>(scheduleService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ScheduleResponseDto> getByName(String name){

        log.info("Find by name: {}", name);
        return new ResponseEntity<>(scheduleService.getByName(name), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody ScheduleRequestDto scheduleRequestDto){

        return new ResponseEntity<>(scheduleService.createSchedule(scheduleRequestDto), HttpStatus.CREATED);
    }

}
