package io.github.joenas.workoutapp.service;

import io.github.joenas.workoutapp.model.workout.Exercise;
import io.github.joenas.workoutapp.model.workout.Workout;
import io.github.joenas.workoutapp.model.workout.WorkoutSet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WorkoutService {

    private static final List<Workout> workouts = new ArrayList<>();


    static {
        workouts.add(new Workout("jonas", "leg day", new Date(),
                List.of(new Exercise("Rack Curls",
                                List.of(new WorkoutSet(1, 10, 80, false),
                                        new WorkoutSet(2, 10, 90, false),
                                        new WorkoutSet(3, 10, 100, false))),
                        new Exercise("Reverse Curls",
                                List.of(new WorkoutSet(1, 10, 100, false),
                                        new WorkoutSet(2, 10, 110, false),
                                        new WorkoutSet(3, 10, 120, false))
                        )
                )));
        workouts.add(new Workout("jonas", "back dat", new Date(),
                List.of(new Exercise("Hammer Curls",
                                List.of(new WorkoutSet(1, 10, 80, false),
                                        new WorkoutSet(2, 10, 90, false),
                                        new WorkoutSet(3, 10, 100, false))),
                        new Exercise("skull crusher",
                                List.of(new WorkoutSet(1, 10, 100, false),
                                        new WorkoutSet(2, 10, 110, false),
                                        new WorkoutSet(3, 10, 120, false))
                        )
                )));
    }

    public List<Workout> getAllWorkouts(){
        return workouts;
    }

    public Workout getWorkoutById(int id) {
        return workouts.get(id);
    }

}
