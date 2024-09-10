package com.fisa.study.management.domain.checkup.repository;

import com.fisa.study.management.domain.checkup.entity.CheckUp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CheckUpRepository extends JpaRepository<CheckUp, Long> {

    Optional<CheckUp> findTopByRoomUuidOrderByIdDesc(UUID uuid);
}
