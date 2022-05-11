package com.panagiotisbrts.commentservice.services;

import com.panagiotisbrts.amqp.RabbitMQMessageProducer;
import com.panagiotisbrts.amqp.model.RabbitMessage;
import com.panagiotisbrts.clients.commentservice.model.CommentDto;
import com.panagiotisbrts.clients.commentservice.model.CommentRequest;
import com.panagiotisbrts.commentservice.domain.Comment;
import com.panagiotisbrts.commentservice.repositories.CommentRepository;
import com.panagiotisbrts.commentservice.web.mappers.CommentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author Panagiotis_Baroutas
 */

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;

    private CommentMapper commentMapper;
    @Mock
    private RabbitMQMessageProducer rabbitMQMessageProducer;

    private CommentServiceImpl service;

    private OffsetDateTime dateTime = OffsetDateTime.now();

    private UUID uuid = UUID.randomUUID();


    @BeforeEach
    void setUp() {
        commentMapper = new CommentMapper();
        service = new CommentServiceImpl(commentRepository, commentMapper, rabbitMQMessageProducer);
    }


    @Test
    void addComment() {

        try (MockedStatic<OffsetDateTime> mockTime = Mockito.mockStatic(OffsetDateTime.class);
             MockedStatic<UUID> mockUUID = Mockito.mockStatic(UUID.class)) {
            mockTime.when(() -> OffsetDateTime.now()).thenReturn(dateTime);
            mockUUID.when(() -> UUID.randomUUID()).thenReturn(uuid);


            CommentRequest req = CommentRequest.builder().commentText("test1").build();
            Comment comment = Comment.builder().commentUUID(uuid.toString())
                    .createdDate(dateTime)
                    .commentText("test1")
                    .build();
            CommentDto dto = CommentDto.builder()
                    .commentText("test1")
                    .commentUUID(uuid.toString())
                    .createdDate(dateTime)
                    .build();

            RabbitMessage message = new RabbitMessage<>(RabbitMessage.Status.CREATED, dto);

            service.addComment(req);
            Mockito.verify(rabbitMQMessageProducer).publish(message,null, "");
            Mockito.verify(commentRepository).saveAndFlush(comment);

        }

    }

}