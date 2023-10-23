package io.github.joenas.workoutapp.workout;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class WorkoutResource {

    WorkoutService workoutService;
    WorkoutRepository workoutRepository;

    Logger logger = LoggerFactory.getLogger(WorkoutResource.class);
    public WorkoutResource(WorkoutService workoutService, WorkoutRepository workoutRepository) {
        this.workoutService = workoutService;
        this.workoutRepository = workoutRepository;
    }

//    Not authenticated
    @GetMapping("/")
    public List<Workout> testall() {
        return workoutRepository.findAll();
    }

//   Authenticated
    @GetMapping("/workouts")
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

//    @GetMapping("/workouts/{username}")
//    public ResponseEntity<List<Workout>> getWorkoutsByUsername(@PathVariable String username) {
//        logger.info("Retrieving workouts for: {}",username);
//        List<Workout> workouts = workoutRepository.findAllByUsername(username);
//        return ResponseEntity.status(HttpStatus.OK).body(workouts);
//    }

    @PostMapping("/workouts/{username}")
    public ResponseEntity<Workout> createWorkout(@RequestBody Workout workout) {
        logger.info("Creating workout for: {}",workout.getUsername());
        Workout savedWorkout = workoutRepository.save(workout);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWorkout);
    }

}
