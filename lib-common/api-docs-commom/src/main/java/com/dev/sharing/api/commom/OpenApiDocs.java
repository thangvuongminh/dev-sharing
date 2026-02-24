package com.dev.sharing.api.commom;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@SecuritySchemes({
    @SecurityScheme(
        name = "oauth2",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
            authorizationCode = @OAuthFlow(
                authorizationUrl = "${api-docs.authorizationUrl}",
                tokenUrl = "${api-docs.tokenUrl}",
                scopes = {@OAuthScope(
                    name = "openid", description = "openid"),
                    @OAuthScope(
                        name = "profile", description = "profile"),
                        @OAuthScope(name = "offline_access", description = "offline_access")
                }
            )

        )
    ),
})
@OpenAPIDefinition(security = @SecurityRequirement(name = "oauth2"),servers = @Server(url = "${api-docs.server}"))
public class OpenApiDocs {
}
