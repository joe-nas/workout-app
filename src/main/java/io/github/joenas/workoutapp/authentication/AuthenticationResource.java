package io.github.joenas.workoutapp.authentication;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationResource {

    // returns the authentication object if the user is authenticated
    Logger logger = org.slf4j.LoggerFactory.getLogger(AuthenticationResource.class);
    @GetMapping("/check-login")
    public ResponseEntity<String> checkLogin(Authentication authentication) {
//        logger.info("Checking login {}", authentication.getName());
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

//        get name returns the google id
        logger.info(authentication.toString());
        return ResponseEntity.status(HttpStatus.OK).body(authentication.toString());
    }



}
