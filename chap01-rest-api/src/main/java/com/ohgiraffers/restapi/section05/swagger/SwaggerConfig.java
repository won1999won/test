package com.ohgiraffers.restapi.section05.swagger;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(SwaggerInfo());

    }
    private Info SwaggerInfo() {
        return new Info()
        .title("Ohgiraffers API")
                .description("Ohgiraffers API").version("1.0.0");
    }
}
