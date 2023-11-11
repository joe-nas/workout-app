package io.github.joenas.workoutapp.service;

import io.github.joenas.workoutapp.model.user.User;
import io.github.joenas.workoutapp.repository.UserRepository;
import io.github.joenas.workoutapp.model.workout.Workout;
import io.github.joenas.workoutapp.repository.WorkoutRepository;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
public class UserService {

    private UserRepository userRepository;
    private WorkoutRepository workoutRepository;
    private MongoTemplate mongoTemplate;

    public UserService(UserRepository userRepository, WorkoutRepository workoutRepository, MongoTemplate mongoTemplate) {
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
        this.mongoTemplate = mongoTemplate;
    }

    Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);

    public Workout saveWorkout(Workout workout, String oauthId) {
        User user = userRepository.findByOauthId(oauthId);
        workout.setUser(user);
        workoutRepository.save(workout);
        return workout;
    }

    public List<Workout> findWorkoutsByOauthId(String oauthId) {
        return workoutRepository.findByOauthId(oauthId);
    }

    public User updateUser(User user, String oauthId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("oauthId").is(oauthId));

        Update update = new Update();
        update.set("username", user.getUsername());
        update.set("email", user.getEmail());
        update.set("profilePictureUrl", user.getMetric());

        return mongoTemplate.findAndModify(query, update, User.class);
    }
}