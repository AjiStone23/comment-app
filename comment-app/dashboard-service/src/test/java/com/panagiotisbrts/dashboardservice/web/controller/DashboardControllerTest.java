package com.panagiotisbrts.dashboardservice.web.controller;

import com.panagiotisbrts.clients.commentservice.model.CommentDto;
import com.panagiotisbrts.clients.commentservice.model.CommentRequest;
import com.panagiotisbrts.clients.commentservice.model.CommentResponse;
import com.panagiotisbrts.dashboardservice.services.DashboardService;
import com.panagiotisbrts.dashboardservice.web.mappers.CommentMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Panagiotis_Baroutas
 */
@AutoConfigureJsonTesters
@WebMvcTest(DashboardController.class)
class DashboardControllerTest {


    @Autowired
    private MockMvc mvc;
    @MockBean
    DashboardService dashboardService;
    @MockBean
    CommentMapper commentMapper;
    @Autowired
    private JacksonTester<List<CommentResponse>> jsonListCommentResponse;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addComment() throws Exception {
        CommentRequest commentRequest = CommentRequest.builder().build();

        mvc.perform(post("/api/v1/dashboard/addComment", commentRequest)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        Mockito.verify(dashboardService).addComment(commentRequest);

    }

    @Test
    void getIndex() throws Exception {
        mvc.perform(get("/api/v1/dashboard/getIndex"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void getLatestComments() throws Exception {

        CommentDto comment1 = CommentDto.builder()
                .commentUUID(UUID.randomUUID().toString())
                .commentText("test1")
                .createdDate(OffsetDateTime.now())
                .build();

        CommentDto comment2 = CommentDto.builder()
                .commentUUID(UUID.randomUUID().toString())
                .commentText("test2")
                .createdDate(OffsetDateTime.now())
                .build();


        CommentResponse commentRes1 = CommentResponse.builder()
                .commentUUID(UUID.randomUUID().toString())
                .commentText("test1")
                .createdDate(OffsetDateTime.now())
                .build();

        CommentResponse commentRes2 = CommentResponse.builder()
                .commentUUID(UUID.randomUUID().toString())
                .commentText("test2")
                .createdDate(OffsetDateTime.now())
                .build();
        List<CommentResponse> commentResponseList = new ArrayList<>();
        commentResponseList.add(commentRes1);
        commentResponseList.add(commentRes2);

        given(dashboardService.getLatestComments(any())).willReturn(Arrays.asList(comment1, comment2));
        given(commentMapper.commentDtoToCommentResponse(comment1)).willReturn(commentRes1);
        given(commentMapper.commentDtoToCommentResponse(comment2)).willReturn(commentRes2);

        mvc.perform(get("/api/v1/dashboard/getLatestComments"))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonListCommentResponse.write(commentResponseList).getJson()));
        Mockito.verify(dashboardService).getLatestComments(any());
    }
}