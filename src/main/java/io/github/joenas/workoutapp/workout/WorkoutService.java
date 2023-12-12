package io.github.joenas.workoutapp.workout;

import io.github.joenas.workoutapp.workout.model.ExerciseModel;
import io.github.joenas.workoutapp.workout.model.WorkoutModel;
import io.github.joenas.workoutapp.workout.model.WorkoutSetModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WorkoutService {

    private static final List<WorkoutModel> workouts = new ArrayList<>();


    static {
        workouts.add(new WorkoutModel("jonas", "leg day", new Date(),
                List.of(new ExerciseModel("Rack Curls",
                                List.of(new WorkoutSetModel(1, 10, 80, false),
                                        new WorkoutSetModel(2, 10, 90, false),
                                        new WorkoutSetModel(3, 10, 100, false))),
                        new ExerciseModel("Reverse Curls",
                                List.of(new WorkoutSetModel(1, 10, 100, false),
                                        new WorkoutSetModel(2, 10, 110, false),
                                        new WorkoutSetModel(3, 10, 120, false))
                        )
                )));
        workouts.add(new WorkoutModel("jonas", "back dat", new Date(),
                List.of(new ExerciseModel("Hammer Curls",
                                List.of(new WorkoutSetModel(1, 10, 80, false),
                                        new WorkoutSetModel(2, 10, 90, false),
                                        new WorkoutSetModel(3, 10, 100, false))),
                        new ExerciseModel("skull crusher",
                                List.of(new WorkoutSetModel(1, 10, 100, false),
                                        new WorkoutSetModel(2, 10, 110, false),
                                        new WorkoutSetModel(3, 10, 120, false))
                        )
                )));
    }

    public List<WorkoutModel> getAllWorkouts(){
        return workouts;
    }

    public WorkoutModel getWorkoutById(int id) {
        return workouts.get(id);
    }

}
