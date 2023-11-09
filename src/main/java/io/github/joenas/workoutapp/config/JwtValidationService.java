package io.github.joenas.workoutapp.config;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

/**
 * Service class for JWT token validation.
 * This class is responsible for validating JWT tokens using a JwtDecoder.
 */
@Service
public class JwtValidationService {

    /**
     * JwtDecoder instance used for decoding JWT tokens.
     */
    private final JwtDecoder jwtDecoder;

    /**
     * Constructor for the JwtValidationService class.
     * @param jwtDecoder JwtDecoder instance for JWT token decoding.
     */
    public JwtValidationService(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    /**
     * Validates a JWT token.
     * This method attempts to decode the provided JWT token. If the decoding fails, a RuntimeException is thrown.
     * @param token JWT token to be validated.
     * @throws RuntimeException if the JWT token is invalid.
     */
    public void validate(String token){
        try{
            Jwt jwt = jwtDecoder.decode(token);
        }catch (JwtException ex){
            throw new RuntimeException("Invalid token " + ex);
        }
    }
}