package com.lucasavs.market_data_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Server gatewayServer = new Server()
                .url("http://localhost:8080/market")
                .description("API Gateway");

        return new OpenAPI()
                .servers(List.of(gatewayServer));
    }
}
