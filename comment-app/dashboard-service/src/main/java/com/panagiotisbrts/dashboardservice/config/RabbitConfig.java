package com.panagiotisbrts.dashboardservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Panagiotis_Baroutas
 */

@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queues.dashboard}")
    private String commentQueue;

    @Value("${rabbitmq.routing-keys.internal-dashboard}")
    private String internalCommentRoutingKey;

    @Bean
    public TopicExchange internalTopicExchange(){
        return new TopicExchange(this.internalExchange);
    }

    @Bean
    public Queue notificationQueue(){
        return new Queue(this.commentQueue);
    }

    @Bean
    public Binding internalToNotificationBinding() {
        return BindingBuilder
                .bind(notificationQueue())
                .to(internalTopicExchange())
                .with(this.internalCommentRoutingKey);
    }

    public String getInternalExchange() {
        return internalExchange;
    }

    public String getCommentQueue() {
        return commentQueue;
    }

    public String getInternalCommentRoutingKey() {
        return internalCommentRoutingKey;
    }
}
