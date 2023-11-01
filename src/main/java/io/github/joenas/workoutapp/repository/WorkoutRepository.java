package io.github.joenas.workoutapp.repository;

import io.github.joenas.workoutapp.model.user.User;
import io.github.joenas.workoutapp.model.workout.Workout;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends MongoRepository<Workout, String>{

    List<Workout> findByUser(User user);
    List<Workout> findByOauthId(String oauthId);
}
