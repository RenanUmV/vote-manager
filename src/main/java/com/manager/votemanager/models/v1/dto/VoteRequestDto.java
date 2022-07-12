package com.manager.votemanager.models.v1.dto;

import com.manager.votemanager.models.v1.enums.VoteEnum;
import lombok.Data;

import javax.persistence.Enumerated;

@Data
public class VoteRequestDto {

    private Long voteSessionId;

    @Enumerated
    private VoteEnum vote;

    private String cpfUser;
}
