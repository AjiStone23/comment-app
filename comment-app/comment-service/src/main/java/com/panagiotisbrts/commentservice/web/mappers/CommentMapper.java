package com.panagiotisbrts.commentservice.web.mappers;

import com.panagiotisbrts.clients.commentservice.model.CommentDto;
import com.panagiotisbrts.clients.commentservice.model.CommentRequest;
import com.panagiotisbrts.clients.commentservice.model.CommentResponse;
import com.panagiotisbrts.commentservice.domain.Comment;
import org.springframework.stereotype.Component;

/**
 * @author Panagiotis_Baroutas
 */
@Component
public class CommentMapper {

    public Comment commentDtoToComment(CommentDto commentDto) {
        return Comment.builder()
                .commentText(commentDto.getCommentText())
                .createdDate(commentDto.getCreatedDate())
                .commentUUID(commentDto.getCommentUUID())
                .build();
    }

    public CommentDto commentToCommentDto(Comment comment) {
        return CommentDto.builder()
                .commentText(comment.getCommentText())
                .createdDate(comment.getCreatedDate())
                .commentUUID(comment.getCommentUUID())
                .build();
    }

    public CommentDto commentRequestToCommentDto(CommentRequest commentRequest) {
        return CommentDto.builder()
                .commentText(commentRequest.getCommentText())
                .build();
    }

    public CommentResponse commentDtoToCommentResponse(CommentDto commentDto) {
        return CommentResponse.builder()
                .commentText(commentDto.getCommentText())
                .createdDate(commentDto.getCreatedDate())
                .commentUUID(commentDto.getCommentUUID())
                .build();
    }

}
