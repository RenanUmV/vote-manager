package com.manager.votemanager.models.v1.dto;

import com.manager.votemanager.models.v1.entity.Vote;
import com.manager.votemanager.models.v1.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private String name;
    private String cpf;
    private RoleEnum role;
    private String email;
    private List<Vote> voteList;

}
