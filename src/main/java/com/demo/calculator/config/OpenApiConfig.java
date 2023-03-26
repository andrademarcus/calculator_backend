package com.demo.calculator.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info =
    @Info(title = "Arithmetic Calculator REST API",
        version = "v1",
        description = "RESTful API - Marcus Andrade")
)
public class OpenApiConfig {

    @Bean
    public OpenApiCustomiser gloablApiCustomiser() {
        return openApi -> openApi.getComponents()
                .addSecuritySchemes("bearer-token",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                );
    }

}