package com.fisa.study.management.domain.comment.dto;

import com.fisa.study.management.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentContent {
    private String content;

    public static CommentContent from(Comment comment) {
        return CommentContent.builder()
                .content(comment.getContent())
                .build();
    }
}
