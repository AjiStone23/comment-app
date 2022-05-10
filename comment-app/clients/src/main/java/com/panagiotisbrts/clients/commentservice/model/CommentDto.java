package com.panagiotisbrts.clients.commentservice.model;

import lombok.*;

import java.time.OffsetDateTime;
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
public class CommentDto {

    private String commentUUID;
    private String commentText;
    private OffsetDateTime createdDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentDto that = (CommentDto) o;
        return Objects.equals(commentUUID, that.commentUUID) && Objects.equals(commentText, that.commentText) && Objects.equals(createdDate, that.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentUUID, commentText, createdDate);
    }
}
