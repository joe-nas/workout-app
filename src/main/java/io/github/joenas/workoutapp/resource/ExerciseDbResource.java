package io.github.joenas.workoutapp.resource;


import io.github.joenas.workoutapp.model.exercisedb.ExerciseDb;
import io.github.joenas.workoutapp.repository.ExerciseDbRepository;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Resource
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api")
@RestController
public class ExerciseDbResource {

    private final ExerciseDbRepository exerciseDbRepository;

    public ExerciseDbResource(ExerciseDbRepository exerciseDbRepository) {
        this.exerciseDbRepository = exerciseDbRepository;
    }

    @GetMapping("/exercisedb/{name}")
    public String retrieveExerciseByName(@PathVariable String name) {
        List<ExerciseDb> exerciseDb = exerciseDbRepository.findAllByForce(name);
        return exerciseDb.toString();
    }

}
