package com.panagiotisbrts.commentservice.services;

import com.panagiotisbrts.amqp.RabbitMQMessageProducer;
import com.panagiotisbrts.commentservice.domain.Comment;
import com.panagiotisbrts.commentservice.repositories.CommentRepository;
import com.panagiotisbrts.commentservice.web.mappers.CommentMapper;
import com.panagiotisbrts.commentservice.web.model.CommentDto;
import com.panagiotisbrts.commentservice.web.model.CommentRequest;
import org.springframework.beans.factory.annotation.Value;
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
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;
    @Value("${rabbitmq.routing-keys.internal-dashboard}")
    private String internalCommentRoutingKey;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper, RabbitMQMessageProducer rabbitMQMessageProducer) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.rabbitMQMessageProducer = rabbitMQMessageProducer;
    }

    @Override
    public void addComment(CommentRequest commentRequest) {
        Comment comment = Comment.builder()
                .commentText(commentRequest.getCommentText())
                .createdDate(OffsetDateTime.now())
                .build();

        commentRepository.save(comment);
        rabbitMQMessageProducer.publish(commentRequest, internalExchange, internalCommentRoutingKey);
    }

    @Override
    public List<CommentDto> getComments() {

        List<CommentDto> commentDtoList = new ArrayList<>();

        List<Comment> commentsList = commentRepository.findAll();

        commentsList.forEach(comment ->  commentDtoList.add(commentMapper.commentToCommentDto(comment)));

        return commentDtoList;
    }
}
