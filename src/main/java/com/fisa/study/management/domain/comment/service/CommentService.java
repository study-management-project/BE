package com.fisa.study.management.domain.comment.service;

import com.fisa.study.management.domain.comment.dto.CommentDTO;

public interface CommentService {
    void regCommentByRoomId(CommentDTO commentDTO);
}
