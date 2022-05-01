package com.panagiotisbrts.commentservice.web.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * @author Panagiotis_Baroutas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {

    private String commentText;

}
