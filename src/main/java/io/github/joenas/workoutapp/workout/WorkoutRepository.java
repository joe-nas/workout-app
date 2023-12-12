package io.github.joenas.workoutapp.workout;

import io.github.joenas.workoutapp.user.UserModel;
import io.github.joenas.workoutapp.workout.model.WorkoutModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutRepository extends MongoRepository<WorkoutModel, String>{

    List<WorkoutModel> findByOauthId(String oauthId);
    void deleteById(String id);
}
