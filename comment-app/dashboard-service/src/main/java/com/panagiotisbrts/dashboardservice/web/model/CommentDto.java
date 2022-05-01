package com.panagiotisbrts.dashboardservice.web.model;

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
public class CommentDto {

    private Long id;
    private String commentText;
    private OffsetDateTime createdDate;
}
