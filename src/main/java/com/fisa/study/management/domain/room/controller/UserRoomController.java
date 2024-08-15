package com.fisa.study.management.domain.room.controller;

import com.fisa.study.management.domain.room.dto.RoomRequestDTO;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.service.RoomService;
import com.fisa.study.management.global.listener.WebSocketEventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserRoomController {

    private final RoomService roomService;
    private final WebSocketEventListener webSocketEventListener;

    @GetMapping("/room/{uuid}")
    public ResponseEntity<?> getRoomByUuId(@PathVariable UUID uuid) {
        return roomService.getRoomContents(uuid);
    }

//    @GetMapping("/room/{uuid}/subscribers")
//    public ResponseEntity<Integer> getSubscriberCountByRoomId(@PathVariable String uuid) {
//        int count = webSocketEventListener.getSubscriberCount(uuid);
//        return ResponseEntity.ok(count);
//    }
}
