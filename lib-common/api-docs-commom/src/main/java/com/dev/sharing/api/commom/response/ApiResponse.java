package com.dev.sharing.api.commom.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ApiResponse<T> {
    ResponseStatus status;
    String message;
    T data;
    String errorCode;
    public static <T> ApiResponse<T> success(T data) {
        var apiResponse = new ApiResponse<T>();
        apiResponse.setStatus(ResponseStatus.SUCCESS);
        apiResponse.setData(data);
        return apiResponse;
    }
    public static <T> ApiResponse<T> error(T data,String message,String errorCode){
        var apiResponse = new ApiResponse<T>();
        apiResponse.setStatus(ResponseStatus.ERROR);
        apiResponse.setData(data);
        apiResponse.setErrorCode(errorCode);
        apiResponse.setMessage(message);
        return apiResponse;
    }
    public static ApiResponse<?> error(String message,String errorCode){
        var apiResponse = new ApiResponse<>();
        apiResponse.setStatus(ResponseStatus.ERROR);
        apiResponse.setErrorCode(errorCode);
        apiResponse.setMessage(message);
        return apiResponse;
    }
}
