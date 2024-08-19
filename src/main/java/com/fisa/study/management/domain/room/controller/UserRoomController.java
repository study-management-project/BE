package com.fisa.study.management.domain.room.controller;

import com.fisa.study.management.domain.room.dto.RoomResponseByUserDTO;
import com.fisa.study.management.domain.room.service.RoomService;
import com.fisa.study.management.global.listener.WebSocketEventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserRoomController {

    private final RoomService roomService;
    private final WebSocketEventListener webSocketEventListener;

    @GetMapping("/room/{uuid}")
    public RoomResponseByUserDTO getRoomByUuId(@PathVariable UUID uuid) {
        return roomService.getRoomDetails(uuid);
    }
}
