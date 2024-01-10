package io.github.joenas.workoutapp.workout.impl;

import io.github.joenas.workoutapp.user.User;
import io.github.joenas.workoutapp.user.UserRepository;
import io.github.joenas.workoutapp.user.exceptions.UserNotFoundException;
import io.github.joenas.workoutapp.workout.WorkoutRepository;
import io.github.joenas.workoutapp.workout.WorkoutService;
import io.github.joenas.workoutapp.workout.model.Workout;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@Setter
public class WorkoutServiceImpl implements WorkoutService {

    WorkoutRepository workoutRepository;
    UserRepository userRepository;

    public WorkoutServiceImpl(WorkoutRepository workoutRepository, UserRepository userRepository) {
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Workout saveWorkout(Workout workout, String oauthId) {
        User user = userRepository.findByOauthId(oauthId);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        workout.setUser(user);
        workoutRepository.save(workout);
        return workout;
    }

    @Override
    public List<Workout> findWorkoutsByOauthId(String oauthId) {

        return workoutRepository.findByOauthId(oauthId);
    }

}
