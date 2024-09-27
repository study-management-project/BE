package com.fisa.study.management.domain.comment.service;

import com.fisa.study.management.domain.comment.dto.CommentContent;
import com.fisa.study.management.domain.comment.dto.CommentDTO;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    void regCommentByRoomId(CommentDTO commentDTO);

    void remove(Long id);

    List<CommentContent> findByUuid(UUID userId);
}
