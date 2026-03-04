package com.example.orderservice.exception;

import com.dev.sharing.api.commom.exception.DevSharingExceptionInfo;
import org.springframework.http.HttpStatus;

public enum ExceptionEnum implements DevSharingExceptionInfo {
  MINIMUM_WITHDRAWAL_NOT_FULFILL("MINIMUM_WITHDRAWAL_NOT_FULFILL","Withdrawal amount does not meet the minimum required of 10 credits. Please ensure the amount is above the minimum withdrawal limit.",HttpStatus.BAD_REQUEST),
  WALLET_NOT_FOUND("WALLET_NOT_FOUND","Wallet not exist",HttpStatus.BAD_REQUEST),
  INSUFFICIENT_BALANCE("INSUFFICIENT_BALANCE","Your balance is insufficient to complete this transaction",HttpStatus.BAD_REQUEST),
  PENDING_WITHDRAWAL_EXISTS("PENDING_WITHDRAWAL_EXISTS","You have a pending withdrawal request. Please wait for the previous request to be processed before making another withdrawal.",HttpStatus.BAD_REQUEST),
  WITHDRAWAL_NOT_EXIST("WITHDRAWAL_NOT_EXIST","Withdrawal not exist",HttpStatus.BAD_REQUEST),
  WITHDRAWAL_NOT_AUTHORIZE("WITHDRAWAL_NOT_AUTHORIZE","You do not have permission to view this withdrawal request",HttpStatus.UNAUTHORIZED),
  WITHDRAWAL_ALREADY_PROCESSED("WITHDRAWAL_ALREADY_PROCESSED","This error occurs when a withdrawal request has already been processed",HttpStatus.CONFLICT),
  ;
  String errorCode;
  String  errorMessage;
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
