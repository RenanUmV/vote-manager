package com.manager.votemanager.models.v1.dto;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
public class UserLoginDto {

    private String email;

    private String password;

    public UsernamePasswordAuthenticationToken converter() {

        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
