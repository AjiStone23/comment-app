package com.panagiotisbrts.dashboardservice.web.controller;

import com.panagiotisbrts.clients.commentservice.CommentResponse;
import com.panagiotisbrts.dashboardservice.services.DashboardService;
import com.panagiotisbrts.dashboardservice.web.mappers.CommentMapper;
import com.panagiotisbrts.dashboardservice.web.model.CommentDto;
import com.panagiotisbrts.dashboardservice.web.model.CommentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
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

    @ModelAttribute
    CommentRequest setupForm () {
        return new CommentRequest();
    }


    @PostMapping(path ="addComment")
    public ResponseEntity addComment(@ModelAttribute CommentRequest commentRequest) {
        log.info("new get comments request Dash");

        dashboardService.addComment(commentRequest);
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping(path ="getComments")
    public String getComments(Model model) {
        log.info("new get comments request");

        List<CommentResponse> commentResponseList = dashboardService.getComments();
        model.addAttribute("comments", commentResponseList);
        return "index";
    }


    @GetMapping(path ="getLatestComments")
    @ResponseBody
    public ResponseEntity<List<CommentResponse>>  getLatestComments() {
        log.info("new get comments request");

        List<CommentDto> commentDtoList = dashboardService.getLatestComments();
        List<CommentResponse> commentResponseList = commentDtoList.stream().map(commentMapper::commentDtoToCommentResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(commentResponseList, HttpStatus.OK);
    }


}
