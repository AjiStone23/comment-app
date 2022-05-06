package com.panagiotisbrts.dashboardservice.rabbitmq;

import com.panagiotisbrts.clients.commentservice.model.CommentDto;
import com.panagiotisbrts.dashboardservice.config.RabbitConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Panagiotis_Baroutas
 */
@Slf4j
@Component
public class CommentConsumer {

    private final RabbitConfig rabbitConfig;
    @Getter
    private static List<CommentDto> newCommentsList = new ArrayList<>();

    public CommentConsumer(RabbitConfig rabbitConfig) {
        this.rabbitConfig = rabbitConfig;
    }

    @RabbitListener(queues = "${rabbitmq.queues.dashboard}")
    public void consumer(CommentDto commentDto) {
        newCommentsList.add(commentDto);
        log.info("Consumed : {} from Queue :  {}",
                commentDto,rabbitConfig.getDashboardQueue());

    }

    public static void resetList() {
        newCommentsList = new ArrayList<>();
    }
}
