package com.example.notifycationservice;

import com.dev.sharing.api.commom.EnableApiDocsCommom;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableDiscoveryClient
@EnableApiDocsCommom
@EnableRetry
public class NotifycationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotifycationServiceApplication.class, args);
    }

}
