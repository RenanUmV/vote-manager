package com.manager.votemanager.controller.v1;

import com.manager.votemanager.models.v1.dto.VoteRequestDto;
import com.manager.votemanager.models.v1.entity.Vote;
import com.manager.votemanager.service.v1.VoteService;
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
