package com.fisa.study.management.domain.comment.controller;

import com.fisa.study.management.domain.comment.dto.CommentDTO;
import com.fisa.study.management.domain.comment.entity.Comment;
import com.fisa.study.management.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/rooms")
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CommentController {
    private final CommentService commentService;

//    @GetMapping("/{id}/addComment")
//    public ResponseEntity<Comment>

    @GetMapping("/{roomId}/comments")
    public List<Comment> getAllComment(@PathVariable Long roomId){
        return commentService.getAllCommentFromRoom(roomId);

    }
    @PostMapping("/{roomId}/addComment")
    public ResponseEntity<Comment> createComment(@PathVariable Long roomId, @RequestBody CommentDTO commentDTO){

        return commentService.createComment(roomId,commentDTO);
    }

}
