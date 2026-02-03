package com.dev.sharing.api.commom;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({
    OpenApiDocs.class,
    OpenApiSecurityWithData.class,
    OpenApiSecurity.class,
})
public @interface EnableApiDocsCommomWithSecurity {

}
