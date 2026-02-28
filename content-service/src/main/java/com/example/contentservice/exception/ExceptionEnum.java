package com.example.contentservice.exception;

import com.dev.sharing.api.commom.exception.DevSharingException;
import com.dev.sharing.api.commom.exception.DevSharingExceptionInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal=true)
public enum ExceptionEnum implements DevSharingExceptionInfo {
    // Content errors
    CONTENT_NOT_FOUND("CONTENT_NOT_FOUND","Content not found",HttpStatus.NOT_FOUND),
    UNAUTHORIZE_CONTENT_ACCESS("UNAUTHORIZE_CONTENT_ACCESS","Unauthorized",HttpStatus.UNAUTHORIZED),
    CONTENT_NO_REVIEW("CONTENT_NO_REVIEW","Content must be submitted for moderator approval",HttpStatus.FORBIDDEN),
    CONTENT_CAN_NOT_SUMMIT("CONTENT_CAN_NOT_SUMMIT","Content can not summit",HttpStatus.CONFLICT),
    // Category errors
    CATEGORY_NOT_FOUND("CATEGORY_NOT_FOUND", "Category not found", HttpStatus.NOT_FOUND),
    // Content Block
    BLOCK_NOT_FOUND("BLOCK_NOT_FOUND","Block not found",HttpStatus.NOT_FOUND),
    PARENT_BLOCK_NOT_IN_SAME_CONTENT("PARENT_BLOCK_NOT_IN_SAME_CONTENT","Parent and child blocks have mismatched content",HttpStatus.BAD_REQUEST),
    ;
    String errorCode;
    String errorMessage;
    HttpStatus httpStatus;
    ExceptionEnum(String errorCode, String errorMessage, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
