package io.github.joenas.workoutapp.config;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
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
            "/check-login",
            "/login",
    };

//    @Bean
//    public JwtDecoder jwtDecoder() {
//
//        JwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri("https://www.googleapis.com/oauth2/v3/certs").build();
//        return new JwtDecoder(){
//            @Override
//            public Jwt decode(String token) throws JwtException {
//                System.out.println("token: " + token);
//                Jwt jwt = jwtDecoder.decode(token);
//                System.out.println("jwt: " + jwt);
//                return jwt;
//            }
//        };
//    }

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
            public void addCorsMappings(CorsRegistry registry) {
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
