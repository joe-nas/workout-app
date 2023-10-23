package io.github.joenas.workoutapp.workout;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document(collection = "workouts")
public class Workout {

    @Id
    private String id;
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

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDataCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", workoutName='" + workoutName + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                ", exercises=" + exercises +
                '}';
    }
}
