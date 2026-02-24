package com.example.identityservice;
import com.dev.sharing.api.commom.EnableApiDocsCommom;
import com.dev.sharing.api.commom.EnableApiDocsCommomWithSecurity;
import com.dev.sharing.api.commom.exception.globalhandler.EnableExceptionHandler;
import feign.Client;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableApiDocsCommomWithSecurity
@EnableExceptionHandler
@EnableFeignClients
@RequiredArgsConstructor
public class IdentityServiceApplication   {
    private final List<Client> clients;
    public static void main(String[] args) {
        SpringApplication.run(IdentityServiceApplication.class, args);
    }

}
