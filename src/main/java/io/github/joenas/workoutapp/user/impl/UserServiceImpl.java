package io.github.joenas.workoutapp.user.impl;

import io.github.joenas.workoutapp.user.User;
import io.github.joenas.workoutapp.user.UserRepository;
import io.github.joenas.workoutapp.user.UserService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class UserServiceImpl implements UserService {
    private MongoTemplate mongoTemplate;
    private UserRepository userRepository;

    Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);

    public UserServiceImpl(MongoTemplate mongoTemplate, UserRepository userRepository) {
        this.mongoTemplate = mongoTemplate;
        this.userRepository = userRepository;
    }


    @Override
    public User updateUser(User user, String oauthId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("oauthId").is(oauthId));

        Update update = new Update();
        update.set("username", user.getUsername());
        update.set("email", user.getEmail());
        update.set("profilePictureUrl", user.getProfilePictureUrl());
        update.set("metric", user.getMetric());

        // maybe throw an exception if the user is not found
        return mongoTemplate.findAndModify(query, update, User.class);
    }

    @Override
    public User findUserByOauthId(String oauthId) {
        return userRepository.findByOauthId(oauthId);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }


}


