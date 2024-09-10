package com.fisa.study.management.domain.comment.service;

import com.fisa.study.management.domain.comment.dto.CommentDTO;
import com.fisa.study.management.domain.comment.entity.Comment;
import com.fisa.study.management.domain.comment.repository.CommentRepository;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.repository.RoomRepository;
import com.fisa.study.management.global.error.CustomException;
import com.fisa.study.management.global.error.ErrorCode;
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

    public void regCommentByRoomId(CommentDTO commentDTO) {
        Room room = roomRepository.findByUuid(commentDTO.getUuid())
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));

        Comment comment = commentDTO.toEntity(room);
        commentRepository.save(comment);
    }
}
