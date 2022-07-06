package com.manager.votemanager.config.security;

import com.manager.votemanager.models.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenSeervice {

    private String expiration = "8640000";

    private String secret = "";

    public String generateToken(Authentication authentication){
        User login = (User) authentication.getPrincipal();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("API vote-manager")
                .setSubject(login.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.ES256, secret)
                .compact();
    }
}
