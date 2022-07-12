package com.manager.votemanager.models.v1.dto;

import com.manager.votemanager.models.v1.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    private String name;
    private String cpf;
    private RoleEnum role;
    private String password;
    private String email;

}
