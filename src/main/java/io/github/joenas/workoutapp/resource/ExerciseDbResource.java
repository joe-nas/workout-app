package io.github.joenas.workoutapp.resource;


import io.github.joenas.workoutapp.model.exercisedb.ExerciseDb;
import jakarta.annotation.Resource;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.Arrays;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RequestMapping("/api")
@RestController
public class ExerciseDbResource {

    @Autowired
    private MongoTemplate mongoTemplate;
    private List<String> flatEntries = List.of("force", "mechanic", "equipment", "category");
    private List<String> arrayEntries = List.of("primaryMuscles", "secondaryMuscles");



    public Document aggreagateUnique(String name){
        TypedAggregation<ExerciseDb> aggregation = Aggregation.newAggregation(
                ExerciseDb.class,
                arrayEntries.contains(name) ?
                        Aggregation
                                .unwind(name) :
                        Aggregation.unwind("$" + name),
                Aggregation
                        .group()
                        .addToSet(name)
                        .as("labels"),
                Aggregation
                        .project()
                        .andExclude("_id")
                        .andInclude("labels")
        );
        AggregationResults<ExerciseDb> uniqueResult = mongoTemplate.aggregate(aggregation, ExerciseDb.class);
        return uniqueResult.getRawResults();
    }

    public Document aggregateCountFlat(String name){
        TypedAggregation<ExerciseDb> aggregation = Aggregation.newAggregation(
                ExerciseDb.class,
                Aggregation
                        .group(name)
                        .count()
                        .as("count"),
                Aggregation
                        .project("count")
                        .and(name)
                        .previousOperation());

        AggregationResults<ExerciseDb> result = mongoTemplate.aggregate(aggregation, ExerciseDb.class);
        return result.getRawResults();
    }

    public Document aggregateCountArray(String name){
        TypedAggregation<ExerciseDb> aggregation = Aggregation.newAggregation(ExerciseDb.class,
                Aggregation
                        .unwind(name),
                Aggregation
                        .group(name)
                        .count()
                        .as("count"),
                Aggregation
                        .project("count")
                        .and(name)
                        .previousOperation());
        AggregationResults<ExerciseDb> result = mongoTemplate.aggregate(aggregation, ExerciseDb.class);
        return result.getRawResults();
    }


    @GetMapping("/stats/{name}")
    public Document receiveStatsFor(@PathVariable String name) {
        if (flatEntries.contains(name)) {
            return aggregateCountFlat(name);
        } throw new IllegalArgumentException("No such entry " + name);
    }

    @GetMapping("/stats/muscle/{name}")
    public Document retriveStatsForMusclesGroups(@PathVariable String name) {
        if (arrayEntries.contains(name)) {
            return aggregateCountArray(name);
        }
        throw new IllegalArgumentException("No such entry " + name);
    }

    @GetMapping("/stats")
    public Document retrieveStats() {
        Document stats = new Document();
        for (String entry : flatEntries) {
            stats.append(entry, receiveStatsFor(entry));
        }
        for (String entry : arrayEntries) {
            stats.append(entry, retriveStatsForMusclesGroups(entry));
        }
        return stats;
    }
}
