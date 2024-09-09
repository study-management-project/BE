package com.fisa.study.management.domain.comment.service;

import com.fisa.study.management.domain.comment.dto.CommentDTO;
import com.fisa.study.management.domain.comment.entity.Comment;
import com.fisa.study.management.domain.comment.repository.CommentRepository;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.repository.RoomRepository;
import com.fisa.study.management.global.error.CustomException;
import com.fisa.study.management.global.error.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final RoomRepository roomRepository;

    public void regCommentByRoomId(CommentDTO commentDTO)  {
        Room room = roomRepository.findByUuid(commentDTO.getUuid())
                .orElseThrow(() ->new CustomException(ErrorCode.ROOM_NOT_FOUND));
        commentRepository.save(DTOToEntityWithRoom(room,commentDTO));

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
