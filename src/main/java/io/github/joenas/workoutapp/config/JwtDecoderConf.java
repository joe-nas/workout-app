package io.github.joenas.workoutapp.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Configuration class for JWT token decoding.
 */
@Configuration
public class JwtDecoderConf {

    /**
     * Secret key for JWT token decoding.
     */
    @Value("${iron-delirium-secret}")
    String secret;

    /**
     * Bean for the JwtDecoder instance.
     * @return JwtDecoder instance.
     */
    @Bean
    public JwtDecoder jwtDecoder() {

        // Base64 encoded secret key
        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).build();
    }
}
