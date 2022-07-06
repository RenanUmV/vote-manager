package com.manager.votemanager.controller;

import com.manager.votemanager.config.security.TokenSeervice;
import com.manager.votemanager.dto.UserLoginDto;
import com.manager.votemanager.models.entity.User;
import com.manager.votemanager.repository.UserRepository;
import com.manager.votemanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    private AuthenticationManager authenticationManager;

    private TokenSeervice tokenService;


    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @GetMapping
    public ResponseEntity<User> getUser(@RequestParam String name){

        return new ResponseEntity<>(userService.getUser(name), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDto dto){
        UsernamePasswordAuthenticationToken dataLogin = dto.converter();

        try{
            Authentication authentication = authenticationManager.authenticate(dataLogin);

            String token = tokenService.generateToken(authentication);

            return ResponseEntity.ok().build();
        }catch (AuthenticationException e){

            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user){
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id){

        userService.deleteUser(id);
    }

}
