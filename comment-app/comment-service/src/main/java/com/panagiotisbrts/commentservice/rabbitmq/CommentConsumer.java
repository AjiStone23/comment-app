package com.panagiotisbrts.commentservice.rabbitmq;

import com.panagiotisbrts.clients.commentservice.model.CommentRequest;
import com.panagiotisbrts.commentservice.services.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Panagiotis_Baroutas
 */
@Slf4j
@Component
public class CommentConsumer {

    private final CommentService commentService;

    public CommentConsumer(CommentService commentService) {
        this.commentService = commentService;
    }

@RabbitListener(queues="${rabbitmq.queues.comment}")
    public void consumer(CommentRequest request) {
        log.info("Consumed : {} from Queue :",
                request);
        commentService.addComment(request);
    }
}
