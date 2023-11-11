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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for OAuth2 security.
 * This class is responsible for setting up the security filter chain and CORS configuration.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class Oauth2SecurityConfiguration {


    private final UserRepository userRepository;
    private final JwtDecoder jwtDecoder;

    /**
     * Whitelist of endpoints that do not require authentication.
     */
    public static final String[] ENDPOINTS_WHITELIST = {
            "/",
//            "/api/user/**",
            "/api/stats/**",
            "/api/exercisedb/**",
            "/api/workouts/**",
    };

//    public static final String[] ALLOWED_ORIGINS = {
//            "http://localhost:3000",
//            "https://iron-delirium.vercel.app"
//    };

    /**
     * Constructor for the OAuth2SecurityConfiguration class.
     *
     * @param userRepository UserRepository instance for user data access.
     * @param jwtDecoder     JwtDecoder instance for JWT token decoding.
     */
    public Oauth2SecurityConfiguration(UserRepository userRepository, JwtDecoder jwtDecoder) {
        this.userRepository = userRepository;
        this.jwtDecoder = jwtDecoder;
    }

    /**
     * Bean for the security filter chain.
     * This method configures the HttpSecurity instance by setting up the request matchers and authentication filters.
     *
     * @param http HttpSecurity instance.
     * @return Configured SecurityFilterChain.
     * @throws Exception if an error occurs during the configuration.
     */

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // in spring boot the default request matcher uses ant patterns
        // requests matched (in whitelist) are not authenticated
        // all other requests are authenticated using oauth2
        http.
                authorizeHttpRequests(
                        (requests) -> requests
                                .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                                .requestMatchers(RequestMethod.OPTIONS.asHttpMethod()).permitAll()
                                .anyRequest().authenticated());
        http.csrf(csrf -> csrf.disable());
        http.addFilterBefore(new CustomJwtAuthenticationFilter(jwtDecoder, userRepository), FilterSecurityInterceptor.class);
//        http.oauth2ResourceServer(oauth2 -> oauth2
//                .jwt(Customizer.withDefaults()));
        return http.build();
    }


    /**
     * Bean for the CORS configuration.
     * This method configures the CORS policy for the application.
     *
     * @return Configured WebMvcConfigurer.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("Authorization", "Accept", "Origin", "Access-Control-Allow-Origin", "Content-Type", "Depth",
                                "User-Agent", "If-Modified-Since", "Cache-Control", "X-Req",
                                "X-File-Size", "X-Requested-With", "X-File-Name")
                        .allowCredentials(true);
            }
        };
    }
}
