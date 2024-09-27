package com.fisa.study.management.domain.comment.controller;

import com.fisa.study.management.domain.comment.dto.CommentContent;
import com.fisa.study.management.domain.comment.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/room/{uuid}/comment")
@Slf4j
@RequiredArgsConstructor
public class CommentController {
    private final CommentServiceImpl commentService;

    //  api인데 룸 첫번째 들어올때 주는거
    @GetMapping()
    public ResponseEntity<?> getCheckUpFirst(@PathVariable UUID uuid) {
        List<CommentContent> commentList = commentService.findByUuid(uuid);
        return ResponseEntity.ok(commentList);
    }

}
