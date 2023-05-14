package com.uniwa.contract.gateway.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@OpenAPIDefinition(
        info = @Info(
                title = "Contract App",
                version = "v1.0",
                description = "The Contract App API"
        ),
        servers = {
                @Server(
                        url = "http://localhost:30090/api",
                        description = "NodePort URL"
                ),
                @Server(
                        url = "",
                        description = "TBD"
                )
        }
)
@Configuration
@Primary
public class OpenApiConfig {

}
