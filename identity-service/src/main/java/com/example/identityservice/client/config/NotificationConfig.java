package com.example.identityservice.client.config;

import com.example.identityservice.config.properties.NotificationsCredentialProperties;
import feign.Logger;
import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationConfig {
    NotificationsCredentialProperties notificationsCredentialProperties;
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new BasicAuthRequestInterceptor(notificationsCredentialProperties.getUsername(), notificationsCredentialProperties.getPassword());
    }
}
