package io.github.joenas.workoutapp.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class Oauth2SecurityConfiguration {

    // these are ant patterns, which is the default way of matching urls in spring security
    public static final String[] ENDPOINTS_WHITELIST = {
            "/",
            "/api/user/**",
            "/api/workouts/**",
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // in spring boot the default request matcher uses ant patterns
        // requests matched (in whitelist) are not authenticated
        // all other requests are authenticated using oauth2
        http.authorizeHttpRequests((requests) ->
                requests
                        .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                        .anyRequest().authenticated());
        http.csrf(csrf->csrf.disable());
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("HEAD","GET", "POST", "PUT", "DELETE", "PATCH")
                        .allowedHeaders("Accept", "Origin","Access-Control-Allow-Origin", "Content-Type", "Depth", "User-Agent", "If-Modified-Since,",
                                "Cache-Control", "Authorization", "X-Req", "X-File-Size", "X-Requested-With", "X-File-Name")
                        .allowCredentials(true);
            }
        };
    }
}
