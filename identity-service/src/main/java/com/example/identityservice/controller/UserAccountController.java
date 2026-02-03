package com.example.identityservice.controller;

import com.dev.sharing.api.commom.response.ApiResponse;
import com.example.identityservice.dto.UserRegisterDto;
import com.example.identityservice.dto.response.UserRegistrationResponseDto;
import com.example.identityservice.service.UserAccountService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/account")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserAccountController {
    UserAccountService userAccountService;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserRegistrationResponseDto>> registerUser(
        @Valid @RequestBody UserRegisterDto userRegisterDto
    ) {
        return ResponseEntity.ok(ApiResponse.success(userAccountService.registerUser(userRegisterDto))) ;
    }
}
