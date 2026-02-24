package com.example.identityservice.config.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "clients.internal.notifications-service")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationsCredentialProperties {
    String username;
    String password;
}
