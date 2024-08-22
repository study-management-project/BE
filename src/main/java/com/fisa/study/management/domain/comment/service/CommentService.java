package com.fisa.study.management.domain.comment.service;

import com.fisa.study.management.domain.comment.dto.CommentDTO;
import com.fisa.study.management.domain.comment.entity.Comment;
import com.fisa.study.management.domain.comment.repository.CommentRepository;

import com.fisa.study.management.domain.room.dto.RoomRequestDTO;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.repository.RoomRepository;
import com.fisa.study.management.domain.room.service.RoomService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentService {
    private final CommentRepository commentRepository;
    private final RoomRepository roomRepository;

    public String regCommentByRoomId(CommentDTO commentDTO)  {
        Room room = roomRepository.findByUuid(commentDTO.getUuid())
                .orElseThrow(() -> new EntityNotFoundException("Room Not Found"));
        commentRepository.save(DTOToEntityWithRoom(room,commentDTO));
        return "코멘트 등록 성공";
    }

    Comment DTOToEntityWithRoom(Room room,CommentDTO commentDTO){
        Comment comment= Comment.builder()
                .content(commentDTO.getContent())
                .room(room)
                .build();
        comment.setRoom(room);
        return comment;
    }
}
