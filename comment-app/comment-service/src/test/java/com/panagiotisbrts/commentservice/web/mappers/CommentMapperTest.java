package com.panagiotisbrts.commentservice.web.mappers;

import com.panagiotisbrts.clients.commentservice.model.CommentDto;
import com.panagiotisbrts.clients.commentservice.model.CommentRequest;
import com.panagiotisbrts.clients.commentservice.model.CommentResponse;
import com.panagiotisbrts.commentservice.domain.Comment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Panagiotis_Baroutas
 */
class CommentMapperTest {

    private CommentMapper commentMapper;

    private OffsetDateTime dateTime = OffsetDateTime.now();

    private UUID uuid = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        commentMapper = new CommentMapper();
    }

    @Test
    void commentDtoToComment() {

        CommentDto dto = CommentDto.builder()
                .createdDate(dateTime)
                .commentUUID(uuid.toString())
                .commentText("test1")
                .build();
        Comment expectedComment = Comment.builder()
                .createdDate(dateTime)
                .commentUUID(uuid.toString())
                .commentText("test1")
                .build();

        Comment actualComment  =  commentMapper.commentDtoToComment(dto);

        Assertions.assertEquals(expectedComment, actualComment);

    }

    @Test
    void commentToCommentDto() {

        CommentDto expectedDto = CommentDto.builder()
                .createdDate(dateTime)
                .commentUUID(uuid.toString())
                .commentText("test1")
                .build();
        Comment comment = Comment.builder()
                .createdDate(dateTime)
                .commentUUID(uuid.toString())
                .commentText("test1")
                .build();

        CommentDto actualDto  =  commentMapper.commentToCommentDto(comment);

        Assertions.assertEquals(expectedDto, actualDto);

    }

    @Test
    void commentRequestToCommentDto() {

        CommentRequest commentRequest = CommentRequest.builder().commentText("test1").build();

        CommentDto expectedDto = CommentDto.builder()
                .commentText("test1")
                .build();

        CommentDto actualDto = commentMapper.commentRequestToCommentDto(commentRequest);
        Assertions.assertEquals(expectedDto, actualDto);
    }
    @Test
    void commentDtoToCommentResponse() {

        CommentDto dto = CommentDto.builder()
                .createdDate(dateTime)
                .commentUUID(uuid.toString())
                .commentText("test1")
                .build();

        CommentResponse expectedResponse = CommentResponse.builder()
                .createdDate(dateTime)
                .commentUUID(uuid.toString())
                .commentText("test1")
                .build();

        CommentResponse actualResponse = commentMapper.commentDtoToCommentResponse(dto);

        Assertions.assertEquals(expectedResponse, actualResponse);
    }
}