package com.example.notifycationservice.controller;

import com.dev.sharing.api.commom.response.ApiResponse;
import com.example.notifycationservice.dto.request.SendNotificationRequestDto;
import com.example.notifycationservice.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("internal/notifications")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationController {
    NotificationService notificationService;
    @PostMapping
    public ResponseEntity<ApiResponse<?>> getNotifications(@RequestBody SendNotificationRequestDto sendNotificationRequestDto) {
        notificationService.SendNotification(sendNotificationRequestDto);
        log.info("Hi someone called notification service");
        return ResponseEntity.ok().body(ApiResponse.success("Send notification success"));
    }
}
