package com.manager.votemanager.repository;

import com.manager.votemanager.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

    Optional<User> findByCpf(String cpf);

    Optional<User> findByEmail(String email);

}
