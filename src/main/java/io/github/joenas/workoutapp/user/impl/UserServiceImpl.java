package io.github.joenas.workoutapp.user.impl;

import io.github.joenas.workoutapp.user.UserModel;
import io.github.joenas.workoutapp.user.UserRepository;
import io.github.joenas.workoutapp.user.UserService;
import io.github.joenas.workoutapp.workout.WorkoutRepository;
import io.github.joenas.workoutapp.workout.model.WorkoutModel;
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
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private WorkoutRepository workoutRepository;
    private MongoTemplate mongoTemplate;

    Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);

    public UserServiceImpl(UserRepository userRepository, WorkoutRepository workoutRepository, MongoTemplate mongoTemplate) {
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public UserModel updateUser(UserModel user, java.lang.String oauthId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("oauthId").is(oauthId));

        Update update = new Update();
        update.set("username", user.getUsername());
        update.set("email", user.getEmail());
        update.set("profilePictureUrl", user.getMetric());

        return mongoTemplate.findAndModify(query, update, UserModel.class);
    }

    @Override
    public List<WorkoutModel> findWorkoutsByOauthId(String oauthId) {
        return workoutRepository.findByOauthId(oauthId);
    }


}


