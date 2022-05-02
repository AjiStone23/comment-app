package com.panagiotisbrts.dashboardservice.services;

import com.panagiotisbrts.amqp.RabbitMQMessageProducer;
import com.panagiotisbrts.clients.commentservice.CommentClient;
import com.panagiotisbrts.clients.commentservice.CommentResponse;
import com.panagiotisbrts.dashboardservice.web.model.CommentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Panagiotis_Baroutas
 */
@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService {


    private final CommentClient commentClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public DashboardServiceImpl(RestTemplate restTemplate, CommentClient commentClient, RabbitMQMessageProducer rabbitMQMessageProducer) {
        this.commentClient = commentClient;
        this.rabbitMQMessageProducer = rabbitMQMessageProducer;
    }

    @Override
    public List<CommentResponse> getComments() {

        List<CommentResponse> commentList = commentClient.getComments().getBody();
        return commentList;
    }


    @Override
    public void addComment(CommentRequest commentRequest) {

        rabbitMQMessageProducer.publish(commentRequest, "internal.exchange", "internal.comment.routing-key");

    }
}
