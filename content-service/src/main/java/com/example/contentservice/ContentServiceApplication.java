package com.example.contentservice;

import com.dev.sharing.api.commom.EnableApiDocsCommom;
import com.dev.sharing.api.commom.EnableApiDocsCommomWithSecurity;
import com.dev.sharing.api.commom.exception.globalhandler.EnableExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableApiDocsCommomWithSecurity
@EnableExceptionHandler
public class ContentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentServiceApplication.class, args);
    }

}
