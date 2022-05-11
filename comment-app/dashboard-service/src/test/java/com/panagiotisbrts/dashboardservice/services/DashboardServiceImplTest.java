package com.panagiotisbrts.dashboardservice.services;

import com.panagiotisbrts.amqp.RabbitMQMessageProducer;
import com.panagiotisbrts.amqp.model.RabbitMessage;
import com.panagiotisbrts.clients.commentservice.model.CommentDto;
import com.panagiotisbrts.clients.commentservice.model.CommentRequest;
import com.panagiotisbrts.dashboardservice.rabbitmq.CommentConsumer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * @author Panagiotis_Baroutas
 */
@ExtendWith(MockitoExtension.class)
class DashboardServiceImplTest {


    DashboardServiceImpl service;

    @Mock
    private RabbitMQMessageProducer rabbitMQMessageProducer;


    @BeforeEach
    void setUp() {
        service = new DashboardServiceImpl(rabbitMQMessageProducer);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addComment() {
        CommentRequest request = CommentRequest.builder().commentText("test1").build();
        RabbitMessage message = new RabbitMessage(RabbitMessage.Status.REQUESTED, request);
        service.addComment(request);
        Mockito.verify(rabbitMQMessageProducer).publish(message, null, null);

    }

    @Test
    void getLatestComments() {

        String matchUUID1 = UUID.randomUUID().toString();
        String matchUUID2 = UUID.randomUUID().toString();
        ConcurrentHashMap<String, CommentDto> map = new ConcurrentHashMap<>();

        CommentDto matchedDto1 = CommentDto.builder().
                commentText("matchedDto1Text")
                .commentUUID(matchUUID1)
                .createdDate(OffsetDateTime.now())
                .build();

        CommentDto matchedDto2 = CommentDto.builder().
                commentText("matchedDto2Text")
                .commentUUID(matchUUID2)
                .createdDate(OffsetDateTime.now())
                .build();

        CommentDto noMatchDto = CommentDto.builder().
                commentText("noMatchDtoText")
                .commentUUID(UUID.randomUUID().toString())
                .createdDate(OffsetDateTime.now())
                .build();

        map.put(matchedDto1.getCommentUUID(), matchedDto1);
        map.put(matchedDto2.getCommentUUID(), matchedDto2);
        map.put(noMatchDto.getCommentUUID(), noMatchDto);
        try (MockedStatic<CommentConsumer> mockConsumer = Mockito.mockStatic(CommentConsumer.class)) {

            mockConsumer.when(CommentConsumer::getNewCommentsMap).thenReturn(map);

            List<String> inputUUIDList = new ArrayList<>();
            inputUUIDList.add(matchUUID1);
            inputUUIDList.add(matchUUID2);

            List<CommentDto> actualResultList = service.getLatestComments(inputUUIDList);

            List<CommentDto> axpectedResultList = new ArrayList<>();
            axpectedResultList.add(noMatchDto);

            Assertions.assertEquals(axpectedResultList, actualResultList);
        }

    }
}