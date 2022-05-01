package com.panagiotisbrts.clients.commentservice;


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

    private Long id;
    private String commentText;
    private OffsetDateTime createdDate;
}
