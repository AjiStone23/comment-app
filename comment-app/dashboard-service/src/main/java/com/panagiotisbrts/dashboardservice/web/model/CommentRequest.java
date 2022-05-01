package com.panagiotisbrts.dashboardservice.web.model;


import lombok.*;


/**
 * @author Panagiotis_Baroutas
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentRequest {

    private String commentText;

}
