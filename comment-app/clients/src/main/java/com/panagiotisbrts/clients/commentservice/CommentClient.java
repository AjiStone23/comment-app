package com.panagiotisbrts.clients.commentservice;

import com.panagiotisbrts.clients.commentservice.model.CommentResponse;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


/**
 * @author Panagiotis_Baroutas
 */

@FeignClient("comment-service")
public interface CommentClient {

    @GetMapping(path = "api/v1/comment/getComments")
    ResponseEntity<List<CommentResponse>> getComments();


}
