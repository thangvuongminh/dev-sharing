package com.example.notifycationservice.service;

import com.example.notifycationservice.dto.request.SendNotificationRequestDto;
import org.springframework.stereotype.Service;


public interface NotificationService {
    public void SendNotification(SendNotificationRequestDto notification);
}
