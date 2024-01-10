package io.github.joenas.workoutapp.workout;

import io.github.joenas.workoutapp.workout.model.Workout;

import java.util.List;

public interface WorkoutService {
    Workout saveWorkout(Workout workout, String oauthId);
    List<Workout> findWorkoutsByOauthId(String oauthId);
}
