package com.panagiotisbrts.commentservice.web.controller;


import com.panagiotisbrts.commentservice.services.CommentService;
import com.panagiotisbrts.commentservice.web.mappers.CommentMapper;
import com.panagiotisbrts.commentservice.web.model.CommentDto;
import com.panagiotisbrts.commentservice.web.model.CommentRequest;
import com.panagiotisbrts.commentservice.web.model.CommentResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Panagiotis_Baroutas
 */

@Slf4j
@RestController
@RequestMapping("/api/v1/comment/")
public class CommentController {


    private final CommentService commentService;
    private final CommentMapper commentMapper;


    public CommentController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @PostMapping(path ="addComment")
    public void addComment(@RequestBody CommentRequest commentRequest) {
        log.info("new add comment request");
        commentService.addComment(commentRequest);

    }

    @GetMapping(path ="getComments")
    public ResponseEntity<List<CommentResponse>> getComments() {
        log.info("new get comments request");

        List<CommentDto> comments = commentService.getComments();

        List<CommentResponse> commentResponseList = comments.stream()
                .map(commentMapper::commentDtoToCommentResponse).collect(Collectors.toList());

        return new ResponseEntity<>(commentResponseList, HttpStatus.OK);
    }

}
