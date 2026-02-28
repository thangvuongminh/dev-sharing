package com.dev.sharing.api.commom.exception.globalhandler;


import com.dev.sharing.api.commom.exception.DevSharingException;
import com.dev.sharing.api.commom.exception.DevSharingExceptionInfo;
import com.dev.sharing.api.commom.response.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DevSharingExceptionHandler {
    MessageSource messageSource;
    @ExceptionHandler(DevSharingException.class)
    public ResponseEntity<ApiResponse<?>> handleDevSharingException(DevSharingException devSharingException){
        DevSharingExceptionInfo ex=devSharingException.getDevSharingExceptionInfo();
        String messages=messageSource.getMessage(ex.getErrorCode(),devSharingException.getArgs(), LocaleContextHolder.getLocale());
        return  ResponseEntity.status(ex.getHttpStatus()).body(ApiResponse.error(messages,ex.getErrorCode()));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        Map<String,String> errors=new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(error->{
            errors.put(error.getField(),error.getDefaultMessage());
        });
        return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.error(errors,null,"VALIDATION ERROR")
        );
    }
  @ExceptionHandler(AuthorizationDeniedException.class)
  public ResponseEntity<ApiResponse<?>> handleAuthorizationDeniedException(AuthorizationDeniedException authorizationDeniedException){
    return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(
        ApiResponse.error("Access deny","ACCESS DENIED")
    );
  }
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<?>> handleExceptionErrorSystem(Exception e){
      e.printStackTrace();
    return   ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(e.getMessage(),"500"));
  }
}
