package homies.com.backend.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI nestBitesAPI() {

        return new OpenAPI()

                .info(

                        new Info()

                                .title("NestBites API")

                                .description("Production Ready Backend APIs for NestBites")

                                .version("1.0.0")

                                .contact(

                                        new Contact()

                                                .name("Harishankar")

                                                .email("harishankar.dev@gmail.com")
                                )
                );
    }
}