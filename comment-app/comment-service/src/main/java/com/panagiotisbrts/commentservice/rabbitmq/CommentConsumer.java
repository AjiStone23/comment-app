package com.panagiotisbrts.commentservice.rabbitmq;

import com.panagiotisbrts.clients.commentservice.model.CommentRequest;
import com.panagiotisbrts.commentservice.config.RabbitConfig;
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

    private final RabbitConfig rabbitConfig;

    public CommentConsumer(CommentService commentService, RabbitConfig rabbitConfig) {
        this.commentService = commentService;
        this.rabbitConfig = rabbitConfig;
    }

@RabbitListener(queues="${rabbitmq.queues.comment}")
    public void consumer(CommentRequest request) {
        log.info("Consumed : {} from Queue : {}",
                request,rabbitConfig.getCommentQueue());
        commentService.addComment(request);
    }
}
