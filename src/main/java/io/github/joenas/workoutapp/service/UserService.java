package io.github.joenas.workoutapp.service;

import io.github.joenas.workoutapp.model.user.User;
import io.github.joenas.workoutapp.repository.UserRepository;
import io.github.joenas.workoutapp.model.workout.Workout;
import io.github.joenas.workoutapp.repository.WorkoutRepository;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
public class UserService {

    private UserRepository userRepository;
    private WorkoutRepository workoutRepository;


    public UserService(UserRepository userRepository, WorkoutRepository workoutRepository) {
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
    }

    Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);

    public Workout saveWorkout(Workout workout, String oauthId) {
        User user = userRepository.findByOauthId(oauthId);
        workout.setUser(user);
        workoutRepository.save(workout);
        return workout;
    }

    public List<Workout> findWorkoutsByOauthId(String oauthId) {
        return workoutRepository.findByOauthId(oauthId);
    }
}