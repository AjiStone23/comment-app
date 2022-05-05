package com.panagiotisbrts.commentservice.domain;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;


/**
 * @author Panagiotis_Baroutas
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Comment {

    @Id
    @SequenceGenerator(
            name= "comment_id_sequence",
            sequenceName = "comment_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_id_sequence"
    )
    private Long id;
    private String commentUUID;
    private String commentText;

    @Column(name = "createdDate", columnDefinition = "timestamp with time zone not null")
    private OffsetDateTime createdDate;

}
