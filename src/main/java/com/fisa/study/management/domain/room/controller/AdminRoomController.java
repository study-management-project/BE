package com.fisa.study.management.domain.room.controller;

import com.fisa.study.management.domain.room.dto.RoomRequestDTO;
import com.fisa.study.management.domain.room.dto.RoomResponseByAdminDTO;
import com.fisa.study.management.domain.room.service.RoomServiceImpl;
import com.fisa.study.management.global.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminRoomController {

    private final RoomServiceImpl roomService;

    // 방 조회 (자신에게 묶인 거 가져오도록 수정해야 함)
    @GetMapping("/rooms")
    public List<RoomResponseByAdminDTO> getAllRooms(@Login Long userId) {
        return roomService.getAllRoomsByUserId(userId);
    }

    // 방 생성
    @PostMapping("/room")
    public ResponseEntity<?> createRoom(@Login Long userId, @RequestBody RoomRequestDTO roomRequestDTO) {
        roomService.createRoom(userId, roomRequestDTO);
        return ResponseEntity.ok("방 생성 성공");
    }
}
