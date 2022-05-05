package com.panagiotisbrts.dashboardservice.web.mappers;

import com.panagiotisbrts.clients.commentservice.model.CommentDto;
import com.panagiotisbrts.clients.commentservice.model.CommentRequest;
import com.panagiotisbrts.clients.commentservice.model.CommentResponse;
import org.springframework.stereotype.Component;

/**
 * @author Panagiotis_Baroutas
 */
@Component
public class CommentMapper {


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
