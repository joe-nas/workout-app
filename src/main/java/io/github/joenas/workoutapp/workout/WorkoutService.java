package io.github.joenas.workoutapp.workout;

import io.github.joenas.workoutapp.workout.model.ExerciseModel;
import io.github.joenas.workoutapp.workout.model.WorkoutModel;
import io.github.joenas.workoutapp.workout.model.WorkoutSetModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public interface WorkoutService {

    List<WorkoutModel> getAllWorkouts();
    WorkoutModel getWorkoutById(int id);
}
