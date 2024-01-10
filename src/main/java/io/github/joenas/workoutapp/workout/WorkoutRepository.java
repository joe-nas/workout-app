package io.github.joenas.workoutapp.workout;

import io.github.joenas.workoutapp.workout.model.Workout;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends MongoRepository<Workout, String>{

    List<Workout> findByOauthId(String oauthId);
    void deleteById(String id);
}
