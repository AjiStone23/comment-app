package com.panagiotisbrts.dashboardservice.rabbitmq;


import com.panagiotisbrts.dashboardservice.web.model.CommentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Panagiotis_Baroutas
 */
@Slf4j
@Component
public class CommentConsumer {




    @RabbitListener(queues="${rabbitmq.queues.dashboard}")
    public void consumer(CommentRequest request) {
        log.info("Consumed : {} from Queue :",
                request);

    }
}
