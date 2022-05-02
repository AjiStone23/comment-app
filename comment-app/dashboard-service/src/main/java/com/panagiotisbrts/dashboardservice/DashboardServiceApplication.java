package com.panagiotisbrts.dashboardservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Panagiotis_Baroutas
 */
@SpringBootApplication   (scanBasePackages = {
        "com.panagiotisbrts.dashboardservice",
        "com.panagiotisbrts.amqp",
        })
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.panagiotisbrts.clients")
public class DashboardServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DashboardServiceApplication.class, args);
    }
}
