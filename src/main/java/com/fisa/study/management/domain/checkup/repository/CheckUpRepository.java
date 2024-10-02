package com.fisa.study.management.domain.checkup.repository;

import com.fisa.study.management.domain.checkup.entity.CheckUp;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CheckUpRepository extends JpaRepository<CheckUp, Long> {


    Optional<CheckUp> findTopByRoomUuidOrderByIdDesc(UUID uuid);

    @EntityGraph(attributePaths = {"room"})
    Optional<CheckUp> findTopWithRoomByRoomUuidOrderByIdDesc(UUID uuid);


//    @Modifying
//    @Transactional
//    @Query("UPDATE CheckUp c SET c.ox.o = c.ox.o + 1 WHERE c.room.uuid = :uuid")
//    void incrementOByRoomUuid(UUID uuid);
//
//    @Modifying
//    @Transactional
//    @Query("UPDATE CheckUp c SET c.ox.x = c.ox.x + 1 WHERE c.room.uuid = :uuid ORDER BY c.id DESC LIMIT 1 ")
//    void incrementXByRoomUuid(@Param("uuid") UUID uuid);
}