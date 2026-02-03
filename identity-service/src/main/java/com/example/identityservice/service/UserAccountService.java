package com.example.identityservice.service;

import com.example.identityservice.dto.UserRegisterDto;
import com.example.identityservice.dto.response.UserRegistrationResponseDto;
import org.springframework.stereotype.Service;

public interface UserAccountService {
    public UserRegistrationResponseDto registerUser(UserRegisterDto userRegisterDto);
}
