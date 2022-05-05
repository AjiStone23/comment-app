package com.panagiotisbrts.clients.commentservice.model;


import lombok.*;

import java.time.OffsetDateTime;


/**
 * @author Panagiotis_Baroutas
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentResponse {

    private String commentUUID;
    private String commentText;
    private OffsetDateTime createdDate;
}
