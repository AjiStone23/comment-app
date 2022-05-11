package com.panagiotisbrts.dashboardservice.rabbitmq;

import com.panagiotisbrts.amqp.model.RabbitMessage;
import com.panagiotisbrts.clients.commentservice.model.CommentDto;
import com.panagiotisbrts.clients.commentservice.model.CommentRequest;
import com.panagiotisbrts.dashboardservice.config.RabbitConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Panagiotis_Baroutas
 */
@Slf4j
@Component
public class CommentConsumer {

    private final RabbitConfig rabbitConfig;
    @Getter
    private static ConcurrentHashMap<String, CommentDto> newCommentsMap = new ConcurrentHashMap<>();

    public CommentConsumer(RabbitConfig rabbitConfig) {
        this.rabbitConfig = rabbitConfig;
    }

    @RabbitListener(queues = "${rabbitmq.queues.dashboard}")
    public void consumer(RabbitMessage<CommentDto> message) {
        newCommentsMap.put(message.getBody().getCommentUUID(), message.getBody());
        log.info("Consumed : {} from Queue :  {}",
                message, rabbitConfig.getDashboardQueue());

    }

    public static void resetMap() {
        newCommentsMap = new ConcurrentHashMap<>();
    }
}
