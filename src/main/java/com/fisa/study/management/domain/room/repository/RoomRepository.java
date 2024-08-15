package com.fisa.study.management.domain.room.repository;

import com.fisa.study.management.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByUuid(UUID uuid);
}
