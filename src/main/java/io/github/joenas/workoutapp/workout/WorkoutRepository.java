package io.github.joenas.workoutapp.workout;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutRepository extends MongoRepository<Workout, String>{
//    List<Workout> findAllByUsername(String username);
}
