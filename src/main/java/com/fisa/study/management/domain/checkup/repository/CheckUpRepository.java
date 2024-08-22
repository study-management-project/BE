package com.fisa.study.management.domain.checkup.repository;

import com.fisa.study.management.domain.checkup.entity.CheckUp;
import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CheckUpRepository extends JpaRepository<CheckUp,Long> {

    Optional<CheckUp> findTopByRoomIdOrderByIdDesc(Long roomId);
}
