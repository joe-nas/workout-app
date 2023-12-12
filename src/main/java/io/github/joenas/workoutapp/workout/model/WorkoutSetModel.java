package io.github.joenas.workoutapp.workout.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkoutSetModel {

    private int setNumber;
    private int reps;
    private double weight;
    private int rpe;
    private boolean isDone;

    public WorkoutSetModel(int setNumber, int reps, double weight, boolean isDone) {
        this.setNumber = setNumber;
        this.reps = reps;
        this.weight = weight;
        this.isDone = isDone;
        this.rpe = -1;
    }

    @Override
    public String toString() {
        return "WorkoutSet{" +
                "setNumber=" + setNumber +
                ", reps=" + reps +
                ", weight=" + weight +
                ", rpe=" + rpe +
                ", isDone=" + isDone +
                '}';
    }
}
