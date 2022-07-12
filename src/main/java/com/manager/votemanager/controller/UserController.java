package com.manager.votemanager.controller;

import com.manager.votemanager.config.security.TokenSeervice;
import com.manager.votemanager.dto.TokenDto;
import com.manager.votemanager.dto.UserLoginDto;
import com.manager.votemanager.models.entity.User;
import com.manager.votemanager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final TokenSeervice tokenService;


    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, TokenSeervice tokenService) {

        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @GetMapping
    public ResponseEntity<User> getUser(@RequestParam String name){

        log.info("Get user by name: {}", name);
        return new ResponseEntity<>(userService.getUser(name), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAll(){

        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){

        log.info("Create user: {}", user.getName());
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid UserLoginDto dto){

        log.info("Login with: {}", dto.getEmail());
        UsernamePasswordAuthenticationToken dataLogin = dto.converter();

        try{
            Authentication authentication = authenticationManager.authenticate(dataLogin);

            String token = tokenService.generateToken(authentication);

            return ResponseEntity.ok(new TokenDto(token, "Bearer"));
        }catch (AuthenticationException e){

            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user){

        log.info("Update user: {}", user.getName());
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){

        log.info("Delete user id: {}", id);
        userService.deleteUser(id);
    }

}
