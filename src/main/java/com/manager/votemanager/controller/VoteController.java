package com.manager.votemanager.controller;

import com.manager.votemanager.dto.VoteRequestDto;
import com.manager.votemanager.models.entity.Vote;
import com.manager.votemanager.service.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/v1/vote")
public class VoteController {

    private final VoteService service;

    @Autowired
    public VoteController(VoteService service) {
        this.service = service;
    }

    @PostMapping("/vote")
    public ResponseEntity<Vote> createVote(@Valid @RequestBody VoteRequestDto dto){

        log.info("User with CPF: {}, voted", dto.getCpfUser());
        return new ResponseEntity<>(service.voting(dto), HttpStatus.CREATED);
    }
}
