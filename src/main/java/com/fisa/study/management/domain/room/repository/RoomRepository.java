package com.fisa.study.management.domain.room.repository;

import com.fisa.study.management.domain.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
