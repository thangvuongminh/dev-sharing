package com.example.identityservice.client.payload;

import com.dev.sharing.api.commom.response.ApiResponse;
import com.example.identityservice.client.config.NotificationConfig;
import com.example.identityservice.client.payload.request.SendNotificationClientRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
@FeignClient(name = "notifycation-service",path = "internal/notifications",configuration = NotificationConfig.class)
public interface NotificationClient {
    @PostMapping
    ResponseEntity<ApiResponse<?>> sendNotification(SendNotificationClientRequest sendNotificationClientRequest);

}
