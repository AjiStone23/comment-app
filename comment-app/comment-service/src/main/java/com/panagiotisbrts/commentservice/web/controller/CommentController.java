package com.panagiotisbrts.commentservice.web.controller;


import com.panagiotisbrts.clients.commentservice.model.CommentDto;
import com.panagiotisbrts.clients.commentservice.model.CommentRequest;
import com.panagiotisbrts.clients.commentservice.model.CommentResponse;
import com.panagiotisbrts.commentservice.services.CommentService;

import com.panagiotisbrts.commentservice.web.mappers.CommentMapper;
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


    @GetMapping(path ="getComments")
    public ResponseEntity<List<CommentResponse>> getComments() {
        log.info("new get comments request");

        List<CommentDto> comments = commentService.getComments();

        List<CommentResponse> commentResponseList = comments.stream()
                .map(commentMapper::commentDtoToCommentResponse).collect(Collectors.toList());

        return new ResponseEntity<>(commentResponseList, HttpStatus.OK);
    }


}
