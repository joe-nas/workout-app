package io.github.joenas.workoutapp.workout;

public class WorkoutSet {

    private int setNumber;
    private int reps;
    private double weight;
    private int rpe;
    private boolean isDone;

    public WorkoutSet(int setNumber, int reps, double weight, boolean isDone) {
        this.setNumber = setNumber;
        this.reps = reps;
        this.weight = weight;
        this.isDone = isDone;
        this.rpe = -1;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(int setNumber) {
        this.setNumber = setNumber;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getRpe() {
        return rpe;
    }

    public void setRpe(int rpe) {
        this.rpe = rpe;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
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
