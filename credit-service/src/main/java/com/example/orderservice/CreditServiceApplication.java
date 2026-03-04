package com.example.orderservice;

import com.dev.sharing.api.commom.EnableApiDocsCommom;
import com.dev.sharing.api.commom.EnableApiDocsCommomWithSecurity;
import com.dev.sharing.api.commom.OpenApiSecurityWithData;
import com.dev.sharing.api.commom.exception.globalhandler.EnableExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableApiDocsCommomWithSecurity
@EnableExceptionHandler
public class CreditServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CreditServiceApplication.class, args);
    }

}
