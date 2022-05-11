package com.panagiotisbrts.dashboardservice.services;

import com.panagiotisbrts.amqp.RabbitMQMessageProducer;
import com.panagiotisbrts.amqp.model.RabbitMessage;
import com.panagiotisbrts.clients.commentservice.CommentClient;
import com.panagiotisbrts.clients.commentservice.model.CommentDto;
import com.panagiotisbrts.clients.commentservice.model.CommentRequest;
import com.panagiotisbrts.dashboardservice.rabbitmq.CommentConsumer;
import com.panagiotisbrts.dashboardservice.web.mappers.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


/**
 * @author Panagiotis_Baroutas
 */
@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService {


    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;
    @Value("${rabbitmq.routing-keys.internal-comment}")
    private String internalCommentRoutingKey;

    public DashboardServiceImpl(RabbitMQMessageProducer rabbitMQMessageProducer) {
        this.rabbitMQMessageProducer = rabbitMQMessageProducer;
    }


    @Override
    public void addComment(CommentRequest commentRequest) {

        RabbitMessage<CommentRequest> message = new RabbitMessage<>(RabbitMessage.Status.REQUESTED, commentRequest);

        rabbitMQMessageProducer.publish(message, internalExchange, internalCommentRoutingKey);

    }

    @Override
    public List<CommentDto> getLatestComments(List<String> commentUUIds) {

        Set<String> commentUUIdsSet = new HashSet<>(commentUUIds);

        ConcurrentHashMap<String, CommentDto> map = CommentConsumer.getNewCommentsMap();

        return map.keySet().stream().filter(key -> !commentUUIdsSet.contains(key))
                .map(map::get).collect(Collectors.toList());

    }


}
