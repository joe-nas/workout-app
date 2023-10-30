package io.github.joenas.workoutapp.user;

import io.github.joenas.workoutapp.workout.Workout;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api")
public class UserResource {

    Logger logger = org.slf4j.LoggerFactory.getLogger(UserResource.class);
    UserRepository userRepository;
    UserService userService;

    public UserResource(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/allusers")
    public List<User> retrieveAllUsers() {
        List<User> users = userRepository.findAll();
        System.out.println(users);
        return users;
    }

    @GetMapping("/user/{oauthId}")
    public ResponseEntity<User> retrieveUserByOauthId(@PathVariable String oauthId) {
        User user = userRepository.findByOauthId(oauthId);
        if (user == null) {
            logger.debug("No user with oauthId: {}", oauthId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            logger.debug("User with oauthId {} is: {}", oauthId, user);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }

    @GetMapping("/user/{oauthId}/workouts")
    public String retrieveWorkoutsByOauthId(@PathVariable String oauthId){
        return "Here are workouts from user with oauthId: " + oauthId;
    }

    @PostMapping("/user/{oauthId}/workouts")
    public String createUserWorkout(@PathVariable String oauthId, @RequestBody Workout workout){
        logger.debug("Creating workout for user with oauthId: {}", oauthId);
        logger.debug("Workout: {}", workout.toString());
        Workout newWorkout = userService.saveWorkout(workout, oauthId);
        return "Creating workout for user with oauthId: " + oauthId + " and workout: " + newWorkout.toString();
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userRepository.save(
                new User(
                        user.getUsername(),
                        user.getEmail(),
                        user.getOauthId()
                )
        );
        logger.debug("Creating user: {}", newUser.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

//    @PostMapping("/user/{oauthId}/edit")
//    public  ResponseEntity<User> editUser(@PathVariable String oauthId, @RequestBody User updatedUserData){
//        User updatedUser = userRepository.updateUserByOauthId(oauthId, updatedUserData);
//        if (updatedUser == null) {
//            logger.debug("No user with oauthId: {}", oauthId);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        } else {
//            logger.debug("User with oauthId {} is: {}", oauthId, updatedUser);
//            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
//        }
//    }

}

