package com.panagiotisbrts.dashboardservice.services;

import com.panagiotisbrts.dashboardservice.web.model.CommentDto;
import com.panagiotisbrts.dashboardservice.web.model.CommentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @author Panagiotis_Baroutas
 */
@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    private final RestTemplate restTemplate;

    public DashboardServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<CommentResponse> getComments() {
        log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

        CommentResponse[] commentsarr = restTemplate.getForObject("http://127.0.0.1:8080/api/v1/comments", CommentResponse[].class);
        log.info(commentsarr.toString());
        return Arrays.asList(commentsarr);
    }
}
