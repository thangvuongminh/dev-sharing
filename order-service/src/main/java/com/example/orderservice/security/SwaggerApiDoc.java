package com.example.orderservice.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.*;
import org.springframework.context.annotation.Configuration;

@Configuration

@SecuritySchemes({
        @SecurityScheme(
                name = "oauth2",
                type = SecuritySchemeType.OAUTH2,
               flows = @OAuthFlows(
                       authorizationCode = @OAuthFlow(
                               authorizationUrl ="http://localhost:8001/oauth2/authorize",
                               tokenUrl = "http://localhost:8001/oauth2/token",
                               scopes = {@OAuthScope(
                                       name = "openid",description = "openid"),
                                       @OAuthScope(
                                               name = "profile",description = "profile")
                               }
                        )

               )
        ),
        @SecurityScheme(
                name = "bearerToken",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "jwt"
        )
})
@OpenAPIDefinition(security = {@SecurityRequirement(name = "basicAuth"),@SecurityRequirement(name="bearerToken")})
public class SwaggerApiDoc {
}
