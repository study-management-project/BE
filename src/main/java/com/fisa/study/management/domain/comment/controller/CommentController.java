package com.fisa.study.management.domain.comment.controller;

import com.fisa.study.management.domain.comment.dto.CommentDTO;
import com.fisa.study.management.domain.comment.entity.Comment;
import com.fisa.study.management.domain.comment.service.CommentService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/room/{uuid}/comment")
@Slf4j
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("")
    public List<CommentDTO> getCommentsByRoomId(@PathVariable UUID uuid) throws IllegalAccessException {
        return commentService.getAllCommentByRoomId(uuid);
    }

//    @GetMapping("/{roomId}/comments")
//    public List<Comment> getAllComment(@PathVariable Long roomId){
//        return commentService.getAllCommentFromRoom(roomId);
//
//    }
//    @PostMapping("/{roomId}/addComment")
//    public ResponseEntity<Comment> createComment(@PathVariable Long roomId, @RequestBody CommentDTO commentDTO){
//
//        return commentService.createComment(roomId,commentDTO);
//    }

}
