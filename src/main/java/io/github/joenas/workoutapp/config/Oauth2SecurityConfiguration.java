package io.github.joenas.workoutapp.config;

import io.github.joenas.workoutapp.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class Oauth2SecurityConfiguration {


    private final UserRepository userRepository;
    private final JwtDecoder jwtDecoder;




    public static final String[] ENDPOINTS_WHITELIST = {
            "/",
            "/api/exercisedb/**",
            "/api/user/**",
            "/api/workouts/**",
    };

    public Oauth2SecurityConfiguration(UserRepository userRepository, JwtDecoder jwtDecoder) {
        this.userRepository = userRepository;
        this.jwtDecoder = jwtDecoder;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // in spring boot the default request matcher uses ant patterns
        // requests matched (in whitelist) are not authenticated
        // all other requests are authenticated using oauth2
        http.
                authorizeHttpRequests(
                        (requests) -> requests
                                .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                                .anyRequest().authenticated());
        http.csrf(csrf -> csrf.disable());
        http.addFilterBefore(new CustomJwtAuthenticationFilter(jwtDecoder, userRepository), FilterSecurityInterceptor.class);
//        http.oauth2ResourceServer(oauth2 -> oauth2
//                .jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH")
                        .allowedHeaders("Accept", "Origin", "Access-Control-Allow-Origin", "Content-Type", "Depth", "User-Agent", "If-Modified-Since,",
                                "Cache-Control", "Authorization", "X-Req", "X-File-Size", "X-Requested-With", "X-File-Name")
                        .allowCredentials(true);
            }
        };
    }

}
