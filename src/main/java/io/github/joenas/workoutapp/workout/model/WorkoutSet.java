package io.github.joenas.workoutapp.workout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class WorkoutSet {

    private int setNumber;
    private int reps;
    private double weight;
    private int rpe;
    private boolean isDone;

}
