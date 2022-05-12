package com.panagiotisbrts.commentservice.domain;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Panagiotis_Baroutas
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentTest {

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
     void saveShouldPersistDataTest(){

        Comment entity = Comment.builder().
                commentText("entityText")
                .commentUUID(UUID.randomUUID().toString())
                .createdDate(OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.UTC).truncatedTo(ChronoUnit.SECONDS))
                .build();

        Comment persistedEntity = this.entityManager.persistFlushFind(entity);
        assertThat(persistedEntity.getCommentText()).isEqualTo(entity.getCommentText());
        assertThat(persistedEntity.getCommentUUID()).isEqualTo(entity.getCommentUUID());
        assertThat(persistedEntity.getCreatedDate()).isEqualTo(entity.getCreatedDate());

    }


}