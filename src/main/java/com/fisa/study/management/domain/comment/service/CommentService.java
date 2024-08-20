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

    public List<CommentDTO> getAllCommentByRoomId(UUID uuid)  {
        Room room= roomRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Room not found"));
        List<Comment> commentList = commentRepository.findAllByRoomIdOrderByIdDesc(room.getId());
        return commentList.stream().map(this::EntityToDTO).collect(Collectors.toList());
    }

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
    CommentDTO EntityToDTO(Comment comment){
        return CommentDTO.builder()
                .content(comment.getContent())
                .build();
    }

    //    public List<Comment> getAllCommentFromRoom(Long roomId){
//        return commentRepository.findByRoomId(roomId);
//    }
//    public ResponseEntity<Comment> createComment(Long roomId,CommentDTO commentDTO) {
//
//        Comment comment= dtoToEntityWithId(roomId,commentDTO);
//        return ResponseEntity.ok(commentRepository.save(comment));
//    }
//
//    public List<Comment> getCommentsByRoomIdAndSelectDate(Long id, LocalDate selectDate){
//
//        return commentRepository.findByRoomIdAndDate(id,selectDate);
//    }


}
