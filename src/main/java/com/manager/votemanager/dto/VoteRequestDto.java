package com.manager.votemanager.dto;

import com.manager.votemanager.models.entity.User;
import com.manager.votemanager.models.enums.VoteEnum;
import lombok.Data;

import javax.persistence.Enumerated;

@Data
public class VoteRequestDto {

    private Long voteSessionId;

    @Enumerated
    private VoteEnum vote;

    private String cpfUser;
}
