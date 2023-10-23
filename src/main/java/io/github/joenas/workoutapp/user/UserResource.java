package io.github.joenas.workoutapp.user;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserResource {

    UserRepository userRepository;

    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/api/users/{email}")
    public User retrieveUSerByEmail(@PathVariable String email) {
        return userRepository.findOneUserByPersonalInformationEmail(email);
    }

}

