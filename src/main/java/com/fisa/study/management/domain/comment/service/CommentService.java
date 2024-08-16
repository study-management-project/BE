package com.fisa.study.management.domain.comment.service;

import com.fisa.study.management.domain.comment.dto.CommentDTO;
import com.fisa.study.management.domain.comment.entity.Comment;
import com.fisa.study.management.domain.comment.repository.CommentRepository;

import com.fisa.study.management.domain.room.dto.RoomRequestDTO;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentService {
    private final CommentRepository commentRepository;
    private final RoomRepository roomRepository;
    public List<Comment> getAllCommentFromRoom(Long roomId){
        return commentRepository.findByRoomId(roomId);
    }
    public ResponseEntity<Comment> createComment(Long roomId,CommentDTO commentDTO) {

        Comment comment= dtoToEntityWithId(roomId,commentDTO);
        return ResponseEntity.ok(commentRepository.save(comment));
    }


    Comment dtoToEntityWithId(Long roomId,CommentDTO commentDTO){
        return Comment.builder()
                .date(commentDTO.getDate())
                .content(commentDTO.getContent())
                .room(roomRepository.findById(roomId).get())
                .build();

    }
    public List<Comment> getCommentsByRoomIdAndSelectDate(Long id, LocalDate selectDate){

        return commentRepository.findByRoomIdAndDate(id,selectDate);
    }

}
