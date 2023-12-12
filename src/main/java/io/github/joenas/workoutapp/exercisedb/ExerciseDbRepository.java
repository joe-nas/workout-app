package io.github.joenas.workoutapp.exercisedb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseDbRepository extends MongoRepository<ExerciseDbModel, String> {
    @Query("{ '$text': { '$search': ?0 } }")
    List<ExerciseDbModel> searchByName(String name);
    List<ExerciseDbModel> findAllByForce(String force);

}
