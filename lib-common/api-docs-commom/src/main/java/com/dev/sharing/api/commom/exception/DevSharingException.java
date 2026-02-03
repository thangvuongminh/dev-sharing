package com.dev.sharing.api.commom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DevSharingException extends RuntimeException{
    private DevSharingExceptionInfo devSharingExceptionInfo;
    private Object[] args;
}
