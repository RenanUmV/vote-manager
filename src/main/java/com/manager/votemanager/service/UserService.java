package com.manager.votemanager.service;

import com.manager.votemanager.models.entity.User;
import com.manager.votemanager.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    CpfService cpfService;

    @Autowired
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User getUser(String name) {

        log.info("Get user by name: {}", name);
        return repository.findByName(name).orElse(null);
    }

    public User getByEmail(String email){

        log.info("Get user by email: {}", email);
        return repository.findByEmail(email).orElse(null);
    }

    public User getUserById(Long id){

        log.info("Get user by id: {}", id);
        return repository.findById(id).orElse(null);
    }

    public User getByCpf(String cpf){

        log.info("Get user by cpf: {}", cpf);
        return repository.findByCpf(cpf).orElse(null);
    }

    public User createUser(User user){

        userValidation(user);

        user.setPassword(encryptPassword(user.getPassword()));

        log.info("User {} has created", user.getName());
        return repository.save(user);
    }

    public User updateUser(User user){

        if (repository.findById(user.getId()).isPresent()){
            Optional<User> findUser = repository.findByEmail(user.getEmail());

            if (findUser.isPresent() && !Objects.equals(findUser.get().getId(), user.getId())){
                log.info("User already exists");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists", null);
            }

            if (Boolean.FALSE.equals(cpfService.validateCpf(user.getCpf()))){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid CPF",null);
            }

            user.setPassword(encryptPassword(user.getPassword()));
            return repository.save(user);
        }
        log.info("User Not Found");
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", null);
    }

    public void deleteUser(Long id){

        repository.deleteById(id);
    }

    private void userValidation(User user) {
        if(repository.findByCpf(user.getCpf()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists with this CPF", null);
        }

        if(repository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists with this email", null);
        }

        if (Boolean.FALSE.equals(cpfService.validateCpf(user.getCpf()))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid CPF",null);
        }
    }

    private String encryptPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
