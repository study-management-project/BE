package com.fisa.study.management.domain.comment.dto;

import com.fisa.study.management.domain.comment.entity.Comment;
import com.fisa.study.management.domain.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private UUID uuid;
    private String content;

    public Comment toEntity(Room room) {
        Comment comment = Comment.builder()
                .content(this.content)
                .build();
        comment.setRoom(room);
        return comment;
    }
}
