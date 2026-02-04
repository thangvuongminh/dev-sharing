package com.example.identityservice;
import com.dev.sharing.api.commom.EnableApiDocsCommom;
import com.dev.sharing.api.commom.EnableApiDocsCommomWithSecurity;
import com.dev.sharing.api.commom.exception.globalhandler.EnableExceptionHandler;
import com.example.identityservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableDiscoveryClient
@EnableApiDocsCommomWithSecurity
@EnableExceptionHandler
public class IdentityServiceApplication   {
    public static void main(String[] args) {
        SpringApplication.run(IdentityServiceApplication.class, args);
    }

}
