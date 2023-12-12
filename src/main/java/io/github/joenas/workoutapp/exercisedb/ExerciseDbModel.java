package io.github.joenas.workoutapp.exercisedb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "exercises")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDbModel {


    @Id
    private @MongoId ObjectId id;
    @TextIndexed
    @Indexed(unique = true)
    private String name;
    private String force;
    private String mechanic;
    private String equipment;
    private List<String> primaryMuscles;
    private List<String> secondaryMuscles;
    private List<String> instructions;
    private String category;
}
