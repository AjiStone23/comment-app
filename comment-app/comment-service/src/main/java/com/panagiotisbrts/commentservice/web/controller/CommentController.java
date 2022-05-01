package com.panagiotisbrts.commentservice.web.controller;

import com.panagiotisbrts.commentservice.domain.Comment;
import com.panagiotisbrts.commentservice.services.CommentService;
import com.panagiotisbrts.commentservice.web.model.CommentRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Panagiotis_Baroutas
 */

@Slf4j
@RestController
@RequestMapping("api/v1/comments")
public class CommentController {


    private final CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public void addComment(@RequestBody CommentRequest commentRequest) {
        log.info("new add comment request");
        commentService.addComment(commentRequest);

    }

    @GetMapping
    public ResponseEntity<List<Comment>> getComments() {
        log.info("new get comments request");

     List<Comment> comments=  commentService.getComments();

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}
