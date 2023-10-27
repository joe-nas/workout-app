package io.github.joenas.workoutapp.workout;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document(collection = "workouts")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Workout {

    @Id
    private @MongoId String id;
    private String username;
    private String workoutName;
    @CreatedDate
    private Date dateCreated;
    @LastModifiedDate
    private Date dateModified;
    private List<Exercise> exercises;

    public Workout(String username, String workoutName, Date dateCreated, List<Exercise> exercises) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.workoutName = workoutName;
        this.dateCreated = dateCreated;
        this.exercises = exercises;
    }

}
