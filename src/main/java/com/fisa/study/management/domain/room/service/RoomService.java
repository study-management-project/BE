package com.fisa.study.management.domain.room.service;

import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RoomService {


    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public String getRoomContents(Long id) {
        Room room = roomRepository.findById(id).orElse(null);
        return room == null ? "" : room.getContent();
    }

    public Room createRoom(String name) {
        Room room = new Room();
        room.setName(name);
        room.setContent("");
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, String content) {
        Room room = roomRepository.findById(id).orElse(null);
        if (room != null) {
            room.setContent(content);
            return roomRepository.save(room);
        }
        return null;
    }
}
