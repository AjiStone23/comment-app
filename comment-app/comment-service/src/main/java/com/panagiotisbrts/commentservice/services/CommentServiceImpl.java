package com.panagiotisbrts.commentservice.services;

import com.panagiotisbrts.commentservice.domain.Comment;
import com.panagiotisbrts.commentservice.repositories.CommentRepository;
import com.panagiotisbrts.commentservice.web.mappers.CommentMapper;
import com.panagiotisbrts.commentservice.web.model.CommentDto;
import com.panagiotisbrts.commentservice.web.model.CommentRequest;
import org.springframework.stereotype.Service;


import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Panagiotis_Baroutas
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
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
    public List<CommentDto> getComments() {

        List<CommentDto> commentDtoList = new ArrayList<>();

        List<Comment> commentsList = commentRepository.findAll();

        commentsList.forEach(comment ->  commentDtoList.add(commentMapper.commentToCommentDto(comment)));

        return commentDtoList;
    }
}
