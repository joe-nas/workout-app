package io.github.joenas.workoutapp.user;

import io.github.joenas.workoutapp.workout.model.WorkoutModel;

import java.util.List;


public interface UserService {



    UserModel updateUser(UserModel user, String oauthId);
    WorkoutModel saveWorkout(WorkoutModel workout, String oauthId);
    List<WorkoutModel> findWorkoutsByOauthId(String oauthId);

}