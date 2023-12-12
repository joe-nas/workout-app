package io.github.joenas.workoutapp.user;

import io.github.joenas.workoutapp.workout.model.WorkoutModel;
import io.github.joenas.workoutapp.workout.WorkoutRepository;
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

    public WorkoutModel saveWorkout(WorkoutModel workout, String oauthId) {
        UserModel user = userRepository.findByOauthId(oauthId);
        workout.setUser(user);
        workoutRepository.save(workout);
        return workout;
    }

    public List<WorkoutModel> findWorkoutsByOauthId(String oauthId) {
        return workoutRepository.findByOauthId(oauthId);
    }

    public UserModel updateUser(UserModel user, String oauthId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("oauthId").is(oauthId));

        Update update = new Update();
        update.set("username", user.getUsername());
        update.set("email", user.getEmail());
        update.set("profilePictureUrl", user.getMetric());

        return mongoTemplate.findAndModify(query, update, UserModel.class);
    }
}