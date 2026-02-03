package com.dev.sharing.api.commom.exception;

import org.springframework.http.HttpStatus;

public interface DevSharingExceptionInfo {
    String getErrorCode();
    String getErrorMessage();
    HttpStatus getHttpStatus();
}
