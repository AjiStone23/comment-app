package com.panagiotisbrts.commentservice.services;

import com.panagiotisbrts.commentservice.domain.Comment;
import com.panagiotisbrts.commentservice.repositories.CommentRepository;
import com.panagiotisbrts.commentservice.web.model.CommentRequest;
import org.springframework.stereotype.Service;


import java.time.OffsetDateTime;
import java.util.List;

/**
 * @author Panagiotis_Baroutas
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void addComment(CommentRequest commentRequest) {
        Comment comment = Comment.builder()
                .commentText(commentRequest.getCommentText())
                .createdDate(OffsetDateTime.now())
                .build();

        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getComments() {
       return commentRepository.findAll();
    }
}
