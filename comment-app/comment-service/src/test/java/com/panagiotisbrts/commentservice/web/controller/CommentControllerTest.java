package com.panagiotisbrts.commentservice.web.controller;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.panagiotisbrts.clients.commentservice.model.CommentDto;
import com.panagiotisbrts.clients.commentservice.model.CommentResponse;
import com.panagiotisbrts.commentservice.services.CommentService;
import com.panagiotisbrts.commentservice.web.mappers.CommentMapper;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @author Panagiotis_Baroutas
 */

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {


    private MockMvc mvc;
    @Mock
    CommentService commentService;
    @Mock
    CommentMapper commentMapper;

    @InjectMocks
    CommentController commentController;
    private JacksonTester<List<CommentResponse>> jsonListCommentResponse;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build());

        mvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void getComments() throws Exception {

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
        given(commentService.getComments()).willReturn(Arrays.asList(comment1, comment2));
        given(commentMapper.commentDtoToCommentResponse(comment1)).willReturn(commentRes1);
        given(commentMapper.commentDtoToCommentResponse(comment2)).willReturn(commentRes2);
//----------------------------------------------------------------------------------------when
        MockHttpServletResponse response = mvc.perform(get("/api/v1/comment/getComments")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

//----------------------------------------------------------------------------------------then
        Mockito.verify(commentService).getComments();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonListCommentResponse.write(commentResponseList).getJson());
    }
}