package io.github.joenas.workoutapp.model.workout;

import java.util.List;

public class Exercise {


    private String exerciseName;
    private List<WorkoutSet> sets;

    public Exercise(String exerciseName, List<WorkoutSet> sets) {
        this.exerciseName = exerciseName;
        this.sets = sets;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public List<WorkoutSet> getSets() {
        return sets;
    }

    public void setSets(List<WorkoutSet> sets) {
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
