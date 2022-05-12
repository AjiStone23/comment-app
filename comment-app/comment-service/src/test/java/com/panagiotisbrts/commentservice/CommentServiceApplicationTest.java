package com.panagiotisbrts.commentservice;

import com.panagiotisbrts.amqp.RabbitMQMessageProducer;
import com.panagiotisbrts.clients.commentservice.model.CommentResponse;
import com.panagiotisbrts.commentservice.domain.Comment;
import com.panagiotisbrts.commentservice.repositories.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


/**
 * @author Panagiotis_Baroutas
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentServiceApplicationTest {


    @MockBean
    private CommentRepository commentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @SpyBean
    private RabbitMQMessageProducer rabbitMQMessageProducer;

    @Test
    void getComments() {

        Comment entity1 = Comment.builder().
                commentText("entityText1")
                .commentUUID(UUID.randomUUID().toString())
                .createdDate(OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS))
                .build();
        Comment entity2 = Comment.builder().
                commentText("entityText2")
                .commentUUID(UUID.randomUUID().toString())
                .createdDate(OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS))
                .build();
        CommentResponse expected1 = CommentResponse.builder()
                .commentText(entity1.getCommentText())
                .commentUUID(entity1.getCommentUUID())
                .createdDate(entity1.getCreatedDate())
                .build();
        CommentResponse expected2 = CommentResponse.builder()
                .commentText(entity2.getCommentText())
                .commentUUID(entity2.getCommentUUID())
                .createdDate(entity2.getCreatedDate())
                .build();

        // ------------------------------------------------------------------------------given
        given(commentRepository.findAll()).willReturn(Arrays.asList(entity1, entity2));
        //------------------------------------------------------------------------------when
        ResponseEntity<CommentResponse[]> response = restTemplate.getForEntity("/api/v1/comment/getComments", CommentResponse[].class);
        //---------------------------------------------------------------------------then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Arrays.asList(response.getBody())).isEqualTo(Arrays.asList(expected1,expected2));

    }
}