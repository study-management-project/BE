package com.fisa.study.management.domain.room.controller;

import com.fisa.study.management.domain.room.dto.RoomModifyRequestDTO;
import com.fisa.study.management.domain.room.dto.RoomRequestDTO;
import com.fisa.study.management.domain.room.dto.RoomResponseByAdminDTO;
import com.fisa.study.management.domain.room.service.RoomServiceImpl;
import com.fisa.study.management.global.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    // 방 삭제
    @DeleteMapping("/room/{uuid}/delete")
    public ResponseEntity<?> deleteRoom(@Login Long userId, @PathVariable UUID uuid) {
        roomService.deleteRoom(uuid);
        return ResponseEntity.ok("방 제거 성공");
    }
    // 방 수정
    @PutMapping("/room/{uuid}/modify")
    public ResponseEntity<?> modifyRoom(@Login Long userId, @RequestBody RoomModifyRequestDTO roomModifyRequestDTO) {
        roomService.modifyRoom(roomModifyRequestDTO);
        return ResponseEntity.ok("방 수정 성공");
    }
}
