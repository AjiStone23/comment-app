package com.panagiotisbrts.commentservice.repositories;

import com.panagiotisbrts.commentservice.domain.Comment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Panagiotis_Baroutas
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentRepositoryTest {

    @Autowired
    CommentRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void findAllShouldReturnAllCommentsTest(){

        Comment entity1 = Comment.builder().
                commentText("entityText")
                .commentUUID(UUID.randomUUID().toString())
                .createdDate(OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS))
                .build();

        Comment entity2 = Comment.builder().
                commentText("entity2Text")
                .commentUUID(UUID.randomUUID().toString())
                .createdDate(OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS))
                .build();

        this.entityManager.persist(entity1);
        this.entityManager.persist(entity2);
        List<Comment> actualList = this.repository.findAll();

        Assertions.assertEquals(2,actualList.size());
        Assertions.assertEquals(entity1.getCommentUUID(),actualList.get(0).getCommentUUID());
        Assertions.assertEquals(entity2.getCommentUUID(),actualList.get(1).getCommentUUID());

    }
}

