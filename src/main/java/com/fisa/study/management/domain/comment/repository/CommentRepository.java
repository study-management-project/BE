package com.fisa.study.management.domain.comment.repository;

import com.fisa.study.management.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByRoomUuid(UUID uuid);
}
