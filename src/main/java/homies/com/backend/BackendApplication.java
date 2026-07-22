package homies.com.backend;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@RestController
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    // Reads MONGODB_URI via Spring's property resolution — this checks,
    // in order: real OS/hosting environment variables first, then the
    // git-ignored application-secrets.properties file. Works the same
    // way locally and in production.
    @Bean
    public MongoClient mongoClient(@Value("${MONGODB_URI:}") String uri) {

        if (uri == null || uri.isEmpty()) {
            throw new IllegalStateException(
                "MONGODB_URI is not set. Set it as an environment variable " +
                "on your host, or add it to a local, git-ignored " +
                "application-secrets.properties file for local dev — " +
                "never hardcode database credentials in source code."
            );
        }

        return MongoClients.create(uri);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:5173",
                                "http://localhost:3000",
                                "https://nestbites.netlify.app"
                        )
                        .allowedMethods(
                                "GET",
                                "POST",
                                "PUT",
                                "DELETE",
                                "OPTIONS"
                        )
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    @GetMapping("/")
    public String home() {
        return "NestBites Backend Chal Raha Hai!";
    }
}