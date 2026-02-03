package com.dev.sharing.api.commom.exception.globalhandler;

import com.dev.sharing.api.commom.OpenApiDocs;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({
       DevSharingExceptionHandler.class,
        MessagesSource.class
})
public @interface EnableExceptionHandler {
}
