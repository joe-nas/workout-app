package io.github.joenas.workoutapp.workout;

import io.github.joenas.workoutapp.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

@Document(collection = "workouts")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Workout {

    @Id
    private @MongoId String id;
    @DBRef
    private User user;
    private String oauthId;
    private String workoutName;
    @CreatedDate
    private Date dateCreated;
    @LastModifiedDate
    private Date dateModified;
    private List<Exercise> exercises;

    public Workout(String workoutName, String oauthId, Date dateCreated, List<Exercise> exercises) {
        this.oauthId = oauthId;
        this.workoutName = workoutName;
        this.dateCreated = dateCreated;
        this.exercises = exercises;
    }

}
