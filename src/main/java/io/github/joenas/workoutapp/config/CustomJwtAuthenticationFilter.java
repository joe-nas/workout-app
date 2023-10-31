package io.github.joenas.workoutapp.config;

import io.github.joenas.workoutapp.user.User;
import io.github.joenas.workoutapp.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * This class is a custom implementation of OncePerRequestFilter which extracts JWT token from the Authorization header
 * of the incoming request, decodes it using the JWK set URI provided in the application properties, retrieves the user
 * details from the UserRepository using the 'sub' claim of the JWT and creates a JwtAuthenticationToken object with the
 * decoded JWT, user authorities and OAuth ID. Finally, it sets the created authentication object in the
 * SecurityContextHolder for further processing of the request.
 */

public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(CustomJwtAuthenticationFilter.class);


    private final JwtDecoder jwtDecoder;
    private final UserRepository userRepository;


    public CustomJwtAuthenticationFilter(JwtDecoder jwtDecoder, UserRepository userRepository) {

        this.jwtDecoder = jwtDecoder;
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");

            try {
                Jwt jwt = jwtDecoder.decode(token);
                String sub = (String) jwt.getClaims().get("sub");
                User user = userRepository.findByOauthId(sub);

                if (user != null && user.getOauthId().equals(sub)) {
                    Authentication authentication = new JwtAuthenticationToken(jwt, List.of(new SimpleGrantedAuthority("ROLE_USER")), user.getOauthId());
                    logger.debug("created authentication object: {}", authentication);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    logger.warn("User not foundL {}", sub);
                }
            } catch (JwtException ex) {
                logger.warn("Invalid token: {}", token);
            }
        }
        filterChain.doFilter(request, response);
    }

}
