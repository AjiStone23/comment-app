package com.panagiotisbrts.dashboardservice.rabbitmq;

import com.panagiotisbrts.clients.commentservice.model.CommentDto;
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

    @Getter
    private static List<CommentDto> newCommentsList = new ArrayList<>();

    @RabbitListener(queues = "${rabbitmq.queues.dashboard}")
    public void consumer(CommentDto commentDto) {
        newCommentsList.add(commentDto);
        log.info("Consumed : {} from Queue :",
                commentDto);

    }

    public static void resetList() {
        newCommentsList = new ArrayList<>();
    }
}
