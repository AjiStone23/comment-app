package com.panagiotisbrts.dashboardservice.web.controller;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.panagiotisbrts.clients.commentservice.model.CommentDto;
import com.panagiotisbrts.clients.commentservice.model.CommentRequest;
import com.panagiotisbrts.clients.commentservice.model.CommentResponse;
import com.panagiotisbrts.dashboardservice.services.DashboardService;
import com.panagiotisbrts.dashboardservice.web.mappers.CommentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Panagiotis_Baroutas
 */

@ExtendWith(MockitoExtension.class)
class DashboardControllerTest {


    private MockMvc mvc;
    @Mock
    private DashboardService dashboardService;
    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private DashboardController dashboardController;

    private JacksonTester<List<CommentResponse>> jsonListCommentResponse;


    @BeforeEach
    void setUp() {

        JacksonTester.initFields(this, JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build());

        mvc = MockMvcBuilders.standaloneSetup(dashboardController).build();

    }

    @Test
    void addComment() throws Exception {
        CommentRequest commentRequest = CommentRequest.builder().build();
//----------------------------------------------------------------------------------------when
        MockHttpServletResponse response = mvc.perform(post("/api/v1/dashboard/addComment", commentRequest)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
//----------------------------------------------------------------------------------------then
     //   Mockito.verify(dashboardService).addComment(commentRequest);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
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
//----------------------------------------------------------------------------------------given
        given(dashboardService.getLatestComments(any())).willReturn(Arrays.asList(comment1, comment2));
        given(commentMapper.commentDtoToCommentResponse(comment1)).willReturn(commentRes1);
        given(commentMapper.commentDtoToCommentResponse(comment2)).willReturn(commentRes2);
//----------------------------------------------------------------------------------------when
        MockHttpServletResponse response = mvc.perform(get("/api/v1/dashboard/getLatestComments").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
//----------------------------------------------------------------------------------------then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonListCommentResponse.write(commentResponseList).getJson());
        Mockito.verify(dashboardService).getLatestComments(any());
    }
}