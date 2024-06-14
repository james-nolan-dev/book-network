package me.nolanjames.booknetworkapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(name = "NJ", email = "nj@nolanjames.me", url = "https://nolanjames.me"),
                description = "OpenApi documentation for Spring Security",
                title = "OpenApi specification - NJ",
                version = "1.0",
                license = @License(name = "NJ", url = "https://nolanjames.me"),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(description = "Local Environment", url = "http://localhost:8088/api/v1"),
                @Server(description = "Production Environment", url = "https://demo.nolanjames.me")
        },
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
