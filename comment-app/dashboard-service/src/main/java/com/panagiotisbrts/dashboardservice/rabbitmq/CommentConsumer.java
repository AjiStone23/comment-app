package com.panagiotisbrts.dashboardservice.rabbitmq;

import com.panagiotisbrts.dashboardservice.web.model.CommentRequest;
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
    private static List<CommentRequest> newCommentsList = new ArrayList<>();

    @RabbitListener(queues = "${rabbitmq.queues.dashboard}")
    public void consumer(CommentRequest request) {
        newCommentsList.add(request);
        log.info("Consumed : {} from Queue :",
                request);

    }

    public static void resetList() {
        newCommentsList = new ArrayList<>();
    }
}
