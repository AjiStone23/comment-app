package com.panagiotisbrts.dashboardservice.web.mappers;

import com.panagiotisbrts.clients.commentservice.CommentResponse;
import com.panagiotisbrts.dashboardservice.web.model.CommentDto;
import com.panagiotisbrts.dashboardservice.web.model.CommentRequest;
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
                .id(commentDto.getId())
                .build();
    }

}
