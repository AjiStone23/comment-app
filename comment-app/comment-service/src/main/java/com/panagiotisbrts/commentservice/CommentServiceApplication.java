package com.panagiotisbrts.commentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Panagiotis_Baroutas
 */
@SpringBootApplication(
        scanBasePackages = {
                "com.panagiotisbrts.commentservice.config",
                "com.panagiotisbrts.amqp",
        })
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.panagiotisbrts.clients")
public class CommentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommentServiceApplication.class, args);
    }


}
