package com.manager.votemanager.service.v1;

import com.manager.votemanager.advice.NotFoundException;
import com.manager.votemanager.models.v1.dto.UserRequestDto;
import com.manager.votemanager.models.v1.dto.UserResponseDto;
import com.manager.votemanager.models.v1.entity.User;
import com.manager.votemanager.repository.v1.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    CpfService cpfService;

    private final UserRepository repository;

    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public UserResponseDto getUser(String name) {

        User user = repository.findByName(name).orElseThrow(() -> new NotFoundException("User note found"));

        log.info("Get user by name: {}", name);
        return modelMapper.map(user, UserResponseDto.class);
    }

    public List<UserResponseDto> getAllUsers() {

        List<User> list = repository.findAll();

        return list.stream().map(
                user -> modelMapper.map(user, UserResponseDto.class)
        ).collect(Collectors.toList());
    }

    public User getUserById(Long id) {

        log.info("Get user by id: {}", id);
        return repository.findById(id).orElseThrow(() -> new NotFoundException("User note found"));
    }

    public User getByCpf(String cpf) {

        log.info("Get user by cpf: {}", cpf);
        return repository.findByCpf(cpf).orElseThrow(() -> new NotFoundException("User note found"));
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        log.info("Creating user: {}", userRequestDto.getName());

        userValidation(userRequestDto);

        userRequestDto.setPassword(encryptPassword(userRequestDto.getPassword()));

        User user = repository.save(buildUser(userRequestDto));

        UserResponseDto userResponseDto = buildResponse(user);

        log.info("User {} has created", userResponseDto.getName());
        return userResponseDto;
    }

    private UserResponseDto buildResponse(User user) {
        return UserResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .cpf(user.getCpf())
                .voteList(user.getVotesList())
                .role(user.getRole()).build();
    }

    private User buildUser(UserRequestDto userRequestDto) {
        return User.builder()
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .cpf(userRequestDto.getCpf())
                .role(userRequestDto.getRole()).build();
    }

    public UserResponseDto updateUser(User user) {

        if (repository.findById(user.getId()).isPresent()) {
            Optional<User> findUser = repository.findByEmail(user.getEmail());

            if (findUser.isPresent() && !Objects.equals(findUser.get().getId(), user.getId())) {
                log.info("User already exists");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists", null);
            }

            if (Boolean.FALSE.equals(cpfService.validateCpf(user.getCpf()))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid CPF", null);
            }

            user.setPassword(encryptPassword(user.getPassword()));

            User userUpdated = repository.save(user);

            return buildResponse(userUpdated);
        }
        log.info("User Not Found");
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", null);
    }

    public void deleteUser(Long id) {

        repository.deleteById(id);
    }

    private void userValidation(UserRequestDto userRequestDto) {
        if (repository.findByCpf(userRequestDto.getCpf()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists with this CPF", null);
        }

        if (repository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists with this email", null);
        }

        if (Boolean.FALSE.equals(cpfService.validateCpf(userRequestDto.getCpf()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid CPF", null);
        }
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
