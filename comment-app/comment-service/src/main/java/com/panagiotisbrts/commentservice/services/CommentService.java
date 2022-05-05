package com.panagiotisbrts.commentservice.services;

import com.panagiotisbrts.clients.commentservice.model.CommentDto;
import com.panagiotisbrts.clients.commentservice.model.CommentRequest;

import java.util.List;

/**
 * @author Panagiotis_Baroutas
 */
public interface CommentService {
    void addComment(CommentRequest commentRequest);

    List<CommentDto> getComments();
}
