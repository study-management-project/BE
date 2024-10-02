package com.fisa.study.management.domain.room.controller;

import com.fisa.study.management.domain.room.dto.RoomResponseByUserDTO;
import com.fisa.study.management.domain.room.service.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserRoomController {

    private final RoomServiceImpl roomService;

    @GetMapping("/room/{uuid}")
    public ResponseEntity<RoomResponseByUserDTO>  getRoomByUuId(@PathVariable UUID uuid) {
        return ResponseEntity.ok(roomService.getRoomDetails(uuid));
    }
}
