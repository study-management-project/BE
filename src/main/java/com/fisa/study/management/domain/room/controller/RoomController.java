package com.fisa.study.management.domain.room.controller;

import com.fisa.study.management.domain.room.dto.RoomRequestDTO;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.service.RoomService;
import com.fisa.study.management.global.listener.WebSocketEventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RoomController {

    private final RoomService roomService;
    private final WebSocketEventListener webSocketEventListener;
    @GetMapping("/rooms")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @PostMapping("/room")
    public ResponseEntity<Room> createRoom(@RequestBody RoomRequestDTO roomRequestDTO) {
        return roomService.createRoom(roomRequestDTO);
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        return roomService.getRoomContents(id);
    }

    @GetMapping("/room/{id}/subscribers")
    public ResponseEntity<Integer> getSubscriberCountByRoomId(@PathVariable Long id) {
        int count = webSocketEventListener.getSubscriberCount(String.valueOf(id));
        return ResponseEntity.ok(count);
    }
}
