package io.github.joenas.workoutapp.exercisedb;


import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RequestMapping("/api")
@RestController
public class ExerciseDbController {

    @Autowired
    private MongoTemplate mongoTemplate;
    private final List<String> flatEntries = List.of("force", "mechanic", "equipment", "category");
    private final List<String> arrayEntries = List.of("primaryMuscles", "secondaryMuscles");



    public Document aggreagateUnique(String name){
        TypedAggregation<ExerciseDbModel> aggregation = Aggregation.newAggregation(
                ExerciseDbModel.class,
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
        AggregationResults<ExerciseDbModel> uniqueResult = mongoTemplate.aggregate(aggregation, ExerciseDbModel.class);
        return uniqueResult.getRawResults();
    }

    public Document aggregateCountFlat(String name){
        TypedAggregation<ExerciseDbModel> aggregation = Aggregation.newAggregation(
                ExerciseDbModel.class,
                Aggregation
                        .group(name)
                        .count()
                        .as("count"),
                Aggregation
                        .project("count")
                        .and(name)
                        .previousOperation());

        AggregationResults<ExerciseDbModel> result = mongoTemplate.aggregate(aggregation, ExerciseDbModel.class);
        return result.getRawResults();
    }

    public Document aggregateCountArray(String name){
        TypedAggregation<ExerciseDbModel> aggregation = Aggregation.newAggregation(ExerciseDbModel.class,
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
        AggregationResults<ExerciseDbModel> result = mongoTemplate.aggregate(aggregation, ExerciseDbModel.class);
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
