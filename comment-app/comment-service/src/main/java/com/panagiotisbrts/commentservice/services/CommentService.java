package com.panagiotisbrts.commentservice.services;

import com.panagiotisbrts.commentservice.domain.Comment;
import com.panagiotisbrts.commentservice.web.model.CommentDto;
import com.panagiotisbrts.commentservice.web.model.CommentRequest;

import java.util.List;

/**
 * @author Panagiotis_Baroutas
 */
public interface CommentService {
    void addComment(CommentRequest commentRequest);

    List<CommentDto> getComments();
}
