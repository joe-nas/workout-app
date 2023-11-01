package io.github.joenas.workoutapp.config;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.stereotype.Service;

@Service
public class JwtValidationService {

    private final JwtDecoder jwtDecoder;

    public JwtValidationService(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    public void validate(String token){
        try{
            Jwt jwt = jwtDecoder.decode(token);
        }catch (JwtException ex){
            throw new RuntimeException("Invalid token {}",ex);
        }
    }
}
