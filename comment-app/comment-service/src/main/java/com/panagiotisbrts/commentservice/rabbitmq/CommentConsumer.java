package com.panagiotisbrts.commentservice.rabbitmq;

import com.panagiotisbrts.commentservice.services.CommentService;
import com.panagiotisbrts.commentservice.web.model.CommentRequest;
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

@RabbitListener(queues="${rabbitmq.queues.notification}")
    public void consumer(CommentRequest request) {
        log.info("Consumed : {} from Queue :",
                request);
        commentService.addComment(request);
    }
}
