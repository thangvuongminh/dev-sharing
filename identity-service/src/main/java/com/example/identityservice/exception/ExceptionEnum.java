package com.example.identityservice.exception;

import com.dev.sharing.api.commom.exception.DevSharingException;
import com.dev.sharing.api.commom.exception.DevSharingExceptionInfo;
import org.springframework.http.HttpStatus;

public enum ExceptionEnum implements DevSharingExceptionInfo {
    USERNAME_ALREADY_EXISTED("USERNAME_ALREADY_EXISTED","This username is already in use",HttpStatus.BAD_REQUEST),
    ;
    String errorCode;
    String errorMessage;
    HttpStatus httpStatus;
    ExceptionEnum(){}
    ExceptionEnum(String errorCode, String errorMessage, HttpStatus httpStatus){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
