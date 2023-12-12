package io.github.joenas.workoutapp.workout.model;

import java.util.List;

public class ExerciseModel {


    private String exerciseName;
    private List<WorkoutSetModel> sets;

    public ExerciseModel(String exerciseName, List<WorkoutSetModel> sets) {
        this.exerciseName = exerciseName;
        this.sets = sets;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public List<WorkoutSetModel> getSets() {
        return sets;
    }

    public void setSets(List<WorkoutSetModel> sets) {
        this.sets = sets;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "exerciseName='" + exerciseName + '\'' +
                ", sets=" + sets +
                '}';
    }
}
