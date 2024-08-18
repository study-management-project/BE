package com.fisa.study.management.domain.room.repository;

import com.fisa.study.management.domain.checkup.entity.CheckUp;
import com.fisa.study.management.domain.comment.entity.Comment;
import com.fisa.study.management.domain.room.dto.RoomResponseByUserDTO;
import com.fisa.study.management.domain.room.dto.RoomResponseByAdminDTO;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByUuid(UUID uuid);
    Optional<Room> findById(Long id);

    @Query("SELECT new com.fisa.study.management.domain.room.dto.RoomResponseByAdminDTO(r.id, r.uuid, r.name, r.description) " +
            "FROM Room r WHERE r.member.id = :adminId")
    List<RoomResponseByAdminDTO> findByAdminId(@Param("adminId") Long adminId);

    @Query("SELECT r FROM Room r LEFT JOIN FETCH r.snapshotList WHERE r.uuid = :uuid")
    Optional<Room> findByUuidWithSnapshots(@Param("uuid") UUID uuid);

    @Query("SELECT r FROM Room r LEFT JOIN FETCH r.commentList WHERE r.uuid = :uuid")
    Optional<Room> findByUuidWithComments(@Param("uuid") UUID uuid);


}

