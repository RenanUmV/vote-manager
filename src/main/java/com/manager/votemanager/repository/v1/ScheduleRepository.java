package com.manager.votemanager.repository.v1;

import com.manager.votemanager.models.v1.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Optional<Schedule> findByName(String name);

    Optional<Schedule> findById(Long id);
}
