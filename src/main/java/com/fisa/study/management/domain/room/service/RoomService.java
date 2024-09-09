package com.fisa.study.management.domain.room.service;

import com.fisa.study.management.domain.room.dto.RoomRequestDTO;
import com.fisa.study.management.domain.room.dto.RoomResponseByAdminDTO;
import com.fisa.study.management.domain.room.dto.RoomResponseByUserDTO;
import com.fisa.study.management.domain.room.entity.Room;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface RoomService {
    List<RoomResponseByAdminDTO> getAllRoomsByUserId(Long userId);

    void createRoom(Long userId, RoomRequestDTO roomRequestDTO);

    void updateRoom(UUID uuid, String content);

    RoomResponseByUserDTO getRoomDetails(UUID uuid);
}
