package com.panagiotisbrts.dashboardservice.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Panagiotis_Baroutas
 */

@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.exchanges.fanout}")
    private String fanoutExchange;

    @Value("${rabbitmq.queues.dashboard}")
    private String dashboardQueue;


    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange(this.fanoutExchange);
    }

    @Bean
    public Queue dashboardQueue(){
        return new Queue(this.dashboardQueue);
    }

    @Bean
    public Binding internalToNotificationBinding() {
        return BindingBuilder
                .bind(dashboardQueue())
                .to(fanout());

    }

    public String getFanoutExchange() {
        return fanoutExchange;
    }

    public String getDashboardQueue() {
        return dashboardQueue;
    }

}
