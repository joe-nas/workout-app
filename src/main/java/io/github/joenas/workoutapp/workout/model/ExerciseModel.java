package io.github.joenas.workoutapp.workout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ExerciseModel {


    private String exerciseName;
    private List<WorkoutSetModel> sets;

    public ExerciseModel(String exerciseName, List<WorkoutSetModel> sets) {
        this.exerciseName = exerciseName;
        this.sets = sets;
    }
}
