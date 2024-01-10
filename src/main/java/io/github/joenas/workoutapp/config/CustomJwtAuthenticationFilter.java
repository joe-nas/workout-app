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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * CustomJwtAuthenticationFilter is a filter that intercepts each request once.
 * It checks if the request contains a JWT token in the Authorization header.
 * If a token is present, it decodes it and sets the authentication in the SecurityContext.
 */
@Component
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(CustomJwtAuthenticationFilter.class);
    private final JwtDecoder jwtDecoder;
    private final UserRepository userRepository;


    /**
     * Constructor for the CustomJwtAuthenticationFilter class.
     * @param jwtDecoder JwtDecoder instance for JWT token decoding.
     * @param userRepository UserRepository instance for user data access.
     */
    public CustomJwtAuthenticationFilter(JwtDecoder jwtDecoder, UserRepository userRepository) {
        this.jwtDecoder = jwtDecoder;
        this.userRepository = userRepository;
    }

    /**
     * This method is called for every request to check if it contains a JWT token in the Authorization header.
     * If a token is present, it decodes it and sets the authentication in the SecurityContext.
     * @param request HttpServletRequest instance.
     * @param response HttpServletResponse instance.
     * @param filterChain FilterChain instance.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");

            try {
                Jwt jwt = jwtDecoder.decode(token.toString());
                String sub = (String) jwt.getClaims().get("sub");
                User user = userRepository.findByOauthId(sub);

                if (user != null && user.getOauthId().equals(sub)) {
                    Authentication authentication = new JwtAuthenticationToken(
                            jwt,
                            user.getUserRoles().stream().map(role -> new SimpleGrantedAuthority(role.toString())).toList(),
                            user.getOauthId());
//                            new SimpleGrantedAuthority(user.getUserRoles().get(0).toString())), user.getOauthId());getOauthId
                    logger.debug("created authentication object: {}", authentication);
                    logger.debug("🎈🎈🎈created authentication object with authorities: {}", authentication.getAuthorities().toString());

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
