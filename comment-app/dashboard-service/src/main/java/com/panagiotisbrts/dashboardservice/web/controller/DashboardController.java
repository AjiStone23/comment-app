package com.panagiotisbrts.dashboardservice.web.controller;

import com.panagiotisbrts.clients.commentservice.model.CommentDto;
import com.panagiotisbrts.clients.commentservice.model.CommentRequest;
import com.panagiotisbrts.clients.commentservice.model.CommentResponse;
import com.panagiotisbrts.dashboardservice.services.DashboardService;
import com.panagiotisbrts.dashboardservice.web.mappers.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Panagiotis_Baroutas
 */
@Slf4j
@Controller
@RequestMapping("/api/v1/dashboard/")
public class DashboardController {

    private final CommentMapper commentMapper;
    private final DashboardService dashboardService;

    public DashboardController(CommentMapper commentMapper, DashboardService dashboardService) {
        this.commentMapper = commentMapper;
        this.dashboardService = dashboardService;
    }


    @PostMapping(path = "addComment")
    public ResponseEntity addComment(CommentRequest commentRequest) {
        log.info("new get comments request Dash");
        dashboardService.addComment(commentRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }


    @GetMapping(path = "getIndex")
    public String getIndex() {
        log.info("new get index request");
        return "index";
    }


    @GetMapping(value = {"getLatestComments", "getLatestComments/{commentUUIds}"})
    @ResponseBody
    public ResponseEntity<List<CommentResponse>> getLatestComments(@PathVariable(required = false) Optional<List<String>> commentUUIds) {
        log.info("new get comments request");

        List<String> commentUUIdsList = commentUUIds.orElseGet(ArrayList::new);

        log.info(commentUUIdsList.toString());
        List<CommentDto> commentDtoList = dashboardService.getLatestComments(commentUUIdsList);
        List<CommentResponse> commentResponseList = commentDtoList.stream().map(commentMapper::commentDtoToCommentResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(commentResponseList, HttpStatus.OK);
    }


}
