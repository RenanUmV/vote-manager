package com.manager.votemanager.repository.v1;

import com.manager.votemanager.models.v1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

    Optional<User> findByCpf(String cpf);

    Optional<User> findByEmail(String email);

}
