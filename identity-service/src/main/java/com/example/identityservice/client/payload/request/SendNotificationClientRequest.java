package com.example.identityservice.client.payload.request;

import com.example.identityservice.model.UserVerificationChannel;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendNotificationClientRequest {
    String content;
    String to;
    String channel;
}
