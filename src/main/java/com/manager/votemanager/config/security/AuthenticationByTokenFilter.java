package com.manager.votemanager.config.security;

import com.manager.votemanager.models.entity.User;
import com.manager.votemanager.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationByTokenFilter extends OncePerRequestFilter {

    private TokenSeervice tokenSeervice;

    private UserService userService;

    public AuthenticationByTokenFilter(TokenSeervice tokenSeervice, UserService userService) {
        this.tokenSeervice = tokenSeervice;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = restoreToken(request);

        boolean valid = tokenSeervice.isValidToken(token);
        if (valid){
            authenticatorUser(token);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticatorUser(String token) {
        Long userId = tokenSeervice.getUserId(token);
        User user = userService.getUserById(userId);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        System.out.println(user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String restoreToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")){
            return null;
        }

        return token.substring(7, token.length());
    }
}
