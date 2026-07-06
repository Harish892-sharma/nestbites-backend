package homies.com.backend.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI nestBitesOpenAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("NestBites API")

                        .description("Production Ready Backend APIs for NestBites")

                        .version("v1.0.0")

                        .contact(new Contact()

                                .name("Harishankar")

                                .email("support@nestbites.in"))

                        .license(new License()

                                .name("MIT License")

                                .url("https://opensource.org/licenses/MIT")))

                .externalDocs(new ExternalDocumentation()

                        .description("NestBites Documentation")

                        .url("https://nestbites.in"));
    }
}