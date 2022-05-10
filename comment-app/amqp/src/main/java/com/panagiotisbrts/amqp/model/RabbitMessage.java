package com.panagiotisbrts.amqp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Panagiotis_Baroutas
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RabbitMessage<T> {
    private Status status;
    private T body;

    public enum Status {
        REQUESTED, CREATED
    }
}
