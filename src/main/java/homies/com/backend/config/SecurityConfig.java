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
                                "/api/upload/**",
                                "/api/payment/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // ==========================
                        // Customer APIs
                        // ==========================
                        .requestMatchers(
                                "/api/cart/**",
                                "/api/address/**",
                                "/api/favorites/**",
                                "/api/reviews/**",
                                "/api/notifications/**"
                        ).hasRole("CUSTOMER")

                        // ==========================
                        // Chef APIs
                        // ==========================
                        .requestMatchers(
                                "/api/v1/menu/**",
                                "/api/v1/chef/**"
                        ).hasRole("CHEF")

                        // ==========================
                        // Customer + Chef
                        // ==========================
                        .requestMatchers(
                                "/api/orders/**"
                        ).hasAnyRole("CUSTOMER", "CHEF")

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