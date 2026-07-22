package homies.com.backend.config;

import homies.com.backend.security.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        // ==========================
                        // Public APIs
                        // ==========================
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/api/home/**",
                                "/api/search/**",

                                // Menu APIs
                                "/api/v1/menu/all",
                                "/api/v1/menu/category/**",
                                "/api/v1/menu/*",

                                // Thali / Tiffin — browsing only (POST/DELETE
                                // require the owning chef, see the Chef APIs
                                // block below).
                                "/api/v1/thali/all",
                                "/api/v1/thali/chef/**",
                                "/api/v1/thali/*",
                                "/api/v1/tiffin/all",
                                "/api/v1/tiffin/chef/**",
                                "/api/v1/tiffin/*",

                                // Public chef listing/profile browsing + reviews of a chef
                                "/api/v1/chef/all",
                                "/api/v1/chef/nearby",
                                "/api/v1/chef/profile/**",
                                "/api/reviews/chef/**",

                                // Swagger
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // ==========================
                        // Customer + Chef (own data only —
                        // enforced in the controllers via CurrentUserUtil)
                        // ==========================
                        .requestMatchers(
                                "/api/cart/**",
                                "/api/address/**",
                                "/api/favorites/**",
                                "/api/reviews/**",
                                "/api/notifications/**",
                                "/api/orders/**",
                                "/api/payment/**",
                                "/api/upload/**",
                                "/api/v1/user/**"
                        ).hasAnyRole("CUSTOMER", "CHEF", "ADMIN")

                        // ==========================
                        // Chef APIs
                        // ==========================
                        .requestMatchers(
                                "/api/v1/menu/**",
                                "/api/v1/thali/**",
                                "/api/v1/tiffin/**",
                                "/api/v1/chef/**"
                        ).hasAnyRole("CHEF", "ADMIN")

                        // ==========================
                        // Admin APIs
                        // ==========================
                        .requestMatchers(
                                "/api/admin/**"
                        ).hasRole("ADMIN")

                        // ==========================
                        // Everything Else
                        // ==========================
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {

        return configuration.getAuthenticationManager();
    }
}