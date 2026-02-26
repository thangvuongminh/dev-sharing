package com.example.contentservice.exception;

import com.dev.sharing.api.commom.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomGlobalHandler {
  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ApiResponse<?>> handleUsernameNotFoundException(UsernameNotFoundException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        ApiResponse.error("User name or password no match","BAD_CREDENTIALS")
    );
  }
}
