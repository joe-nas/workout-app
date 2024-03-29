package io.github.joenas.workoutapp.workout;


import io.github.joenas.workoutapp.workout.model.Workout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class WorkoutController {

    WorkoutService workoutService;
    WorkoutRepository workoutRepository;

    Logger logger = LoggerFactory.getLogger(WorkoutController.class);
    public WorkoutController(WorkoutService workoutService, WorkoutRepository workoutRepository) {
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

    @PostMapping("/workouts/create")
    public ResponseEntity<Workout> createWorkout(@RequestBody Workout workout) {
        logger.info("Creating workout for: {}",workout.getOauthId());
        Workout savedWorkout = workoutRepository.save(workout);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWorkout);
    }

    @DeleteMapping("/workouts/delete/{id}")
    public ResponseEntity<String> deleteWorkout(@PathVariable String id) {
        logger.info("Deleting workout with id: {}",id);
        workoutRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted workout with id: " + id);
    }

}
