package com.fisa.study.management.domain.room.service;

import com.fisa.study.management.domain.comment.entity.Comment;
import com.fisa.study.management.domain.comment.repository.CommentRepository;
import com.fisa.study.management.domain.room.dto.RoomRequestDTO;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoomService {
    private final RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public ResponseEntity<Room> getRoomContents(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isPresent()) return ResponseEntity.ok(room.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<Room> createRoom(RoomRequestDTO roomRequestDTO) {
        Room room = roomRequestDTO.toEntity();
        return ResponseEntity.ok(roomRepository.save(room));
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
