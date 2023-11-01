package io.github.joenas.workoutapp.repository;

import io.github.joenas.workoutapp.model.exercisedb.ExerciseDb;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseDbRepository extends MongoRepository<ExerciseDb, String> {
    @Query("{ '$text': { '$search': ?0 } }")
    ExerciseDb searchByName(String name);

    List<ExerciseDb> findAllByForce(String force);

}
