package io.github.joenas.workoutapp.user;

import io.github.joenas.workoutapp.workout.model.WorkoutModel;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api")
public class UserController {

    Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);
    UserRepository userRepository;
    UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

//    @GetMapping("/user/allusers")
    public List<UserModel> retrieveAllUsers() {
        List<UserModel> users = userRepository.findAll();
        System.out.println(users);
        return users;
    }

    @GetMapping("/user/{oauthId}")
    public ResponseEntity<UserModel> retrieveUserByOauthId(Authentication auth, @PathVariable String oauthId) {
        logger.debug("Retrieving user with {}", auth);
        UserModel user = userRepository.findByOauthId(oauthId);
        if (user == null) {
            logger.debug("No user with oauthId: {}", oauthId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            logger.debug("User with oauthId {} is: {}", oauthId, user);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }

    @GetMapping("/user/{oauthId}/workouts")
    public ResponseEntity<List<WorkoutModel>> retrieveWorkoutsByOauthId(@PathVariable String oauthId) {
        logger.debug("🏋️🏋️🏋️ Finding workouts from user with oauthId: {}", oauthId);
        List<WorkoutModel> workouts = userService.findWorkoutsByOauthId(oauthId);
        return ResponseEntity.status(HttpStatus.OK).body(workouts);
//        return "Here are workouts from user with oauthId: " + oauthId;
    }

    @PostMapping("/user/{oauthId}/workouts")
    public String createUserWorkout(@PathVariable String oauthId, @RequestBody WorkoutModel workout) {
        logger.debug("Creating workout for user with oauthId: {}", oauthId);
        logger.debug("Workout: {}", workout.toString());
        WorkoutModel newWorkout = userService.saveWorkout(workout, oauthId);
        return "Creating workout for user with oauthId: " + oauthId + " and workout: " + newWorkout.toString();
    }

    @PostMapping("/user/create")
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        logger.debug("🥩🥩🥩 Trying to create user: {}", user.toString());
        UserModel newUser = userRepository.save(
                new UserModel(
                        user.getUsername(),
                        user.getEmail(),
                        user.getOauthId(),
                        user.getOauthDetails()
                )
        );
        logger.debug("🥩🥩🥩 The newUser is: {}", newUser.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @GetMapping("/user/check/{oauthId}")
    public ResponseEntity<Void> checkIfUserExists(@PathVariable String oauthId) {
        logger.debug("🦝🦝🦝🐮🐮🐮Checking if user with oauthId: {} exists", oauthId);
        UserModel user = userRepository.findByOauthId(oauthId);
        if (user == null) {
            logger.debug("🦝🦝🦝🦝🦝🦝User with oauthId: {} does not exist", oauthId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            logger.debug("🐮🐮🐮🐮🐮🐮User with oauthId: {} exists", oauthId);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }


    @GetMapping("/security")
    @PreAuthorize("hasRole('USER')")
    public String securityTest(Authentication auth){
        logger.debug(userRepository.findByOauthId(auth.getName()).toString());
        logger.debug("Security test: {}", auth);
        return "Security test";
    }

    @PutMapping("/user/{oauthId}/profile")
    @PreAuthorize("hasRole('USER')")
    public String updateUser(@PathVariable String oauthId, @RequestBody UserModel user) {
        logger.debug("Updating user with oauthId: {}", oauthId);
        logger.debug("User: {}", user.toString());
        UserModel updatedUser = userService.updateUser(user, oauthId);
        return "Updating user with oauthId: " + oauthId + " and user: " + updatedUser.toString();
    }
}
