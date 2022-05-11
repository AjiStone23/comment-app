package com.panagiotisbrts.commentservice.web.controller;

import com.panagiotisbrts.clients.commentservice.model.CommentDto;
import com.panagiotisbrts.clients.commentservice.model.CommentResponse;
import com.panagiotisbrts.commentservice.services.CommentService;
import com.panagiotisbrts.commentservice.web.mappers.CommentMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Panagiotis_Baroutas
 */

@AutoConfigureJsonTesters
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    CommentService commentService;
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

        given(commentService.getComments()).willReturn(Arrays.asList(comment1, comment2));
        given(commentMapper.commentDtoToCommentResponse(comment1)).willReturn(commentRes1);
        given(commentMapper.commentDtoToCommentResponse(comment2)).willReturn(commentRes2);
        mvc.perform(get("/api/v1/comment/getComments")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).
                andExpect(content().string(jsonListCommentResponse.write(commentResponseList).getJson()));

        Mockito.verify(commentService).getComments();

    }
}