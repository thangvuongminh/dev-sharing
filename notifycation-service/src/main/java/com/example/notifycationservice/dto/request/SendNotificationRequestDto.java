package com.example.notifycationservice.dto.request;

import com.example.notifycationservice.model.NotificationChannel;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendNotificationRequestDto {
    String content;
    String to;
    String channel;
}
