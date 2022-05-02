package com.panagiotisbrts.dashboardservice.web.controller;

import com.panagiotisbrts.clients.commentservice.CommentResponse;
import com.panagiotisbrts.dashboardservice.services.DashboardService;
import com.panagiotisbrts.dashboardservice.web.mappers.CommentMapper;
import com.panagiotisbrts.dashboardservice.web.model.CommentRequest;
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
@RequestMapping("/api/v1/dashboard/")
public class DashboardController {

    private final CommentMapper commentMapper;
    private final DashboardService dashboardService;

    public DashboardController(CommentMapper commentMapper, DashboardService dashboardService) {
        this.commentMapper = commentMapper;
        this.dashboardService = dashboardService;
    }


    @PostMapping(path ="addComment")
    public ResponseEntity addComment(@RequestBody CommentRequest commentRequest) {
        log.info("new get comments request Dash");

        dashboardService.addComment(commentRequest);
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping(path ="getComments")
    public ResponseEntity<List<CommentResponse>> getComments() {
        log.info("new get comments request");

        List<CommentResponse> commentResponseList = dashboardService.getComments();
        return new ResponseEntity<>(commentResponseList, HttpStatus.OK);
    }


}
