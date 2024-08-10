package com.fisa.study.management.domain.room.controller;

import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @PostMapping("/{name}")
    public String addRoom(@PathVariable String name) {
        roomService.createRoom(name);
        return "ok";
    }

    @GetMapping("/{id}")
    public String getRoomById(@PathVariable Long id) {
        return roomService.getRoomContents(id);
    }

    @MessageMapping("/room/{id}/update")
    @SendTo("/topic/room/{id}")
    public Room updateRoom(@DestinationVariable Long id, String content) {
        return roomService.updateRoom(id, content);
    }
}
