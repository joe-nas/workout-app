package io.github.joenas.workoutapp.workout.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Exercise {


    private String exerciseName;
    private List<WorkoutSet> sets;

    public Exercise(String exerciseName, List<WorkoutSet> sets) {
        this.exerciseName = exerciseName;
        this.sets = sets;
    }
}
