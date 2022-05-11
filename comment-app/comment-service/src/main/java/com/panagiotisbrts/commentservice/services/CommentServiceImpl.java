package com.panagiotisbrts.commentservice.services;

import com.panagiotisbrts.amqp.RabbitMQMessageProducer;
import com.panagiotisbrts.amqp.model.RabbitMessage;
import com.panagiotisbrts.clients.commentservice.model.CommentDto;
import com.panagiotisbrts.clients.commentservice.model.CommentRequest;
import com.panagiotisbrts.commentservice.domain.Comment;
import com.panagiotisbrts.commentservice.repositories.CommentRepository;

import com.panagiotisbrts.commentservice.web.mappers.CommentMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Panagiotis_Baroutas
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    @Value("${rabbitmq.exchanges.fanout}")
    private String fanoutExchange;

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
                .commentUUID(UUID.randomUUID().toString())
                .build();

        commentRepository.saveAndFlush(comment);

        CommentDto commentDto = commentMapper.commentToCommentDto(comment);
        RabbitMessage<CommentDto> message = new RabbitMessage<>(RabbitMessage.Status.CREATED, commentDto);

        rabbitMQMessageProducer.publish(message, fanoutExchange, "");
    }

    @Override
    public List<CommentDto> getComments() {

        List<CommentDto> commentDtoList = new ArrayList<>();

        List<Comment> commentsList = commentRepository.findAll();

        commentsList.forEach(comment -> commentDtoList.add(commentMapper.commentToCommentDto(comment)));

        return commentDtoList;
    }
}
