package com.fisa.study.management.domain.comment.repository;

import com.fisa.study.management.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
//    List<Comment> findByRoomId(Long roomId);
//    @Query("SELECT c FROM Comment c WHERE c.room = :room AND  = :selectDate")
//    List<Comment> findByRoomIdAndDate( Long roomId, LocalDate selectDate);

}
