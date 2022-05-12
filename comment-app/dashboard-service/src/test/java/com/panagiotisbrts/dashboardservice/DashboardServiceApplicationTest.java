package com.panagiotisbrts.dashboardservice;

import com.panagiotisbrts.amqp.RabbitMQMessageProducer;
import com.panagiotisbrts.amqp.model.RabbitMessage;
import com.panagiotisbrts.clients.commentservice.model.CommentDto;
import com.panagiotisbrts.clients.commentservice.model.CommentRequest;
import com.panagiotisbrts.clients.commentservice.model.CommentResponse;
import com.panagiotisbrts.dashboardservice.web.controller.DashboardController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * @author Panagiotis_Baroutas
 */
@SpringBootTest
class DashboardServiceApplicationTest {

    @Autowired
    private DashboardController controller;

    @SpyBean
    private RabbitMQMessageProducer rabbitMQMessageProducer;

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;
    @Value("${rabbitmq.routing-keys.internal-comment}")
    private String internalCommentRoutingKey;

    @Value("${rabbitmq.exchanges.fanout}")
    private String fanoutExchange;

    @Test
    void afterRequestPublishingMessage() {
        CommentRequest req = CommentRequest.builder().commentText("test").build();
        controller.addComment(req);
        RabbitMessage message = new RabbitMessage(RabbitMessage.Status.REQUESTED, req);
        Mockito.verify(rabbitMQMessageProducer).publish(message, internalExchange, internalCommentRoutingKey);
    }

    @Test
    void consumingMessagesAndFilteringAfterRequest() throws InterruptedException {

        String matchUUID1 = UUID.randomUUID().toString();
        String matchUUID2 = UUID.randomUUID().toString();


        CommentDto matchedDto1 = CommentDto.builder().
                commentText("matchedDto1Text")
                .commentUUID(matchUUID1)
                .createdDate(OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.UTC))
                .build();

        CommentDto matchedDto2 = CommentDto.builder().
                commentText("matchedDto2Text")
                .commentUUID(matchUUID2)
                .createdDate(OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.UTC))
                .build();

        CommentDto noMatchDto = CommentDto.builder().
                commentText("noMatchDtoText")
                .commentUUID(UUID.randomUUID().toString())
                .createdDate(OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.UTC))
                .build();


        RabbitMessage<CommentDto> message1 = new RabbitMessage<>(RabbitMessage.Status.CREATED, matchedDto1);
        RabbitMessage<CommentDto> message2 = new RabbitMessage<>(RabbitMessage.Status.CREATED, matchedDto2);
        RabbitMessage<CommentDto> message3 = new RabbitMessage<>(RabbitMessage.Status.CREATED, noMatchDto);
        rabbitMQMessageProducer.publish(message1, fanoutExchange, "");
        rabbitMQMessageProducer.publish(message2, fanoutExchange, "");
        rabbitMQMessageProducer.publish(message3, fanoutExchange, "");

        TimeUnit.SECONDS.sleep(3);

        List<String> UUIDList = new ArrayList<>();
        UUIDList.add(matchUUID1);
        UUIDList.add(matchUUID2);
        Optional<List<String>> input = Optional.of(UUIDList);

        ResponseEntity<List<CommentResponse>> res = controller.getLatestComments(input);


        List<CommentResponse> expectedResultList = new ArrayList<>();
        expectedResultList.add(CommentResponse.builder()
                .commentText(noMatchDto.getCommentText())
                .createdDate(noMatchDto.getCreatedDate())
                .commentUUID(noMatchDto.getCommentUUID()).build());

        Assertions.assertEquals(expectedResultList, res.getBody());
        Assertions.assertEquals(HttpStatus.OK, res.getStatusCode());
    }


}