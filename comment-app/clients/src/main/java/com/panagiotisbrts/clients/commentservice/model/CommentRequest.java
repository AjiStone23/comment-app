package com.panagiotisbrts.clients.commentservice.model;


import lombok.*;

import java.util.Objects;


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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentRequest that = (CommentRequest) o;
        return Objects.equals(commentText, that.commentText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentText);
    }
}
