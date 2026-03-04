package com.example.contentservice.config;

import feign.Logger;
import feign.RequestInterceptor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Configuration
@Log4j2
public class FeignClientConfig {
  @Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }
  @Bean
  public RequestInterceptor bearerTokenInterceptor() {
    return requestTemplate -> {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication instanceof  JwtAuthenticationToken jwtAuthenticationToken) {
        requestTemplate.header("Authorization", "Bearer " + jwtAuthenticationToken.getToken());
      }else {
        log.info("No Bearer Token");
      }
    };
  }
}
