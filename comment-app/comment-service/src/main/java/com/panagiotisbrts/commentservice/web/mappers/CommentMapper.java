package com.panagiotisbrts.commentservice.web.mappers;

import com.panagiotisbrts.commentservice.domain.Comment;
import com.panagiotisbrts.commentservice.web.model.CommentDto;
import com.panagiotisbrts.commentservice.web.model.CommentRequest;
import com.panagiotisbrts.commentservice.web.model.CommentResponse;
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
                .id(commentDto.getId())
                .build();
    }

    public CommentDto commentToCommentDto(Comment comment) {
        return CommentDto.builder()
                .commentText(comment.getCommentText())
                .createdDate(comment.getCreatedDate())
                .id(comment.getId())
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
                .id(commentDto.getId())
                .build();
    }

}
