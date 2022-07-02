package com.manager.votemanager.controller;

import com.manager.votemanager.dto.SessionRequestDto;
import com.manager.votemanager.dto.SessionStartRequestDto;
import com.manager.votemanager.models.entity.VotingSession;
import com.manager.votemanager.service.VotingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/session")
public class VotingSessionController {

    private final VotingSessionService service;

    @Autowired
    public VotingSessionController(VotingSessionService service) {
        this.service = service;
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<VotingSession> getById(@PathVariable Long id){
//
//        return new ResponseEntity<>(service.verifyExistentSchedule(id), HttpStatus.OK);
//    }

    @GetMapping("/all")
    public ResponseEntity<List<VotingSession>> getAll(){

        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<VotingSession> createSession(@Valid @RequestBody SessionRequestDto dto){

        return new ResponseEntity<>(service.createSession(dto), HttpStatus.CREATED);
    }

    @PostMapping("/start")
    public ResponseEntity<VotingSession> startSession(@Valid @RequestBody SessionStartRequestDto dto){

        return new ResponseEntity<>(service.startSession(dto), HttpStatus.OK);
    }
}
