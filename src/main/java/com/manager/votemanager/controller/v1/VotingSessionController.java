package com.manager.votemanager.controller.v1;

import com.manager.votemanager.models.v1.dto.SessionRequestDto;
import com.manager.votemanager.models.v1.dto.SessionStartRequestDto;
import com.manager.votemanager.models.v1.entity.VotingSession;
import com.manager.votemanager.service.v1.VotingSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/session")
@Slf4j
public class VotingSessionController {

    private final VotingSessionService service;

    @Autowired
    public VotingSessionController(VotingSessionService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<VotingSession> getById(@PathVariable Long id){

        log.info("Find session by Id {}", id);
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<VotingSession>> getAll(){

        log.info("Find All Sessions");
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<VotingSession> createSession(@Valid @RequestBody SessionRequestDto dto){

        log.info("Create a new Voting Session");
        return new ResponseEntity<>(service.createSession(dto), HttpStatus.CREATED);
    }

    @PostMapping("/start")
    public ResponseEntity<VotingSession> startSession(@Valid @RequestBody SessionStartRequestDto dto){

        log.info("Voting session {} has been iniciated", dto.getSessionId());
        return new ResponseEntity<>(service.startSession(dto), HttpStatus.OK);
    }
}
