package com.fisa.study.management.domain.room.repository;

import com.fisa.study.management.domain.room.dto.RoomResponseByAdminDTO;
import com.fisa.study.management.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByUuid(UUID uuid);

    @Query("SELECT new com.fisa.study.management.domain.room.dto.RoomResponseByAdminDTO(r.id, r.uuid, r.name, r" +
            ".description) " +
            "FROM Room r WHERE r.member.id = :adminId")
    List<RoomResponseByAdminDTO> findByAdminId(@Param("adminId") Long adminId);

    void deleteByUuid(UUID uuid);
}

