package io.github.joenas.workoutapp.workout.model;

import io.github.joenas.workoutapp.user.UserModel;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.nio.file.attribute.FileAttribute;
import java.util.Date;
import java.util.List;

@Document(collection = "workouts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class WorkoutModel {

    @Id
    private @MongoId String id;
    @DBRef
    private UserModel user;
    private String oauthId;
    private String workoutName;
    @CreatedDate
    private Date dateCreated;
    @LastModifiedDate
    private Date dateModified;
    private List<ExerciseModel> exercises;

    public WorkoutModel(String workoutName, String oauthId, Date dateCreated, List<ExerciseModel> exercises) {
        this.oauthId = oauthId;
        this.workoutName = workoutName;
        this.dateCreated = dateCreated;
        this.exercises = exercises;
    }
}
