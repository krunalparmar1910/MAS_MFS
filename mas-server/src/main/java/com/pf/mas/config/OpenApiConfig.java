package com.pf.mas.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    private static final String OAUTH_SCHEME_NAME = "my_oAuth_security_schema";
    @Value("${keycloak.server-uri}")
    private String authServerUrl;
    @Value("${keycloak.realm}")
    private String realm;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(createComponents())
                .addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME_NAME))
                .info(createInfo());
    }

    private SecurityScheme createOAuthScheme() {
        OAuthFlows flows = createOAuthFlows();
        return new SecurityScheme().type(SecurityScheme.Type.OAUTH2)
                .flows(flows);
    }

    private OAuthFlows createOAuthFlows() {
        OAuthFlow flow = createAuthorizationCodeFlow();
        return new OAuthFlows().authorizationCode(flow);
    }

    private OAuthFlow createAuthorizationCodeFlow() {
        return new OAuthFlow()
                .authorizationUrl(authServerUrl + "/realms/" + realm + "/protocol/openid-connect/auth")
                .tokenUrl(authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token");
    }

    private Components createComponents() {
        return new Components()
                .addSecuritySchemes(OAUTH_SCHEME_NAME, createOAuthScheme());
    }

    private Info createInfo() {
        return new Info()
                .title("MAS MFS")
                .description("MAS MFS APIs")
                .version("1.0");
    }
}
