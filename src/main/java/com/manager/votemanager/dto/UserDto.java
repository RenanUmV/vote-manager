package com.manager.votemanager.dto;

import com.manager.votemanager.models.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String name;
    private String cpf;
    private RoleEnum role;

}
