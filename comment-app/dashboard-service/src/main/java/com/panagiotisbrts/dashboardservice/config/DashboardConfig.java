package com.panagiotisbrts.dashboardservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Panagiotis_Baroutas
 */
@Configuration
public class DashboardConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
