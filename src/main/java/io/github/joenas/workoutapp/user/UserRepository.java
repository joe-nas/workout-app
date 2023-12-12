package io.github.joenas.workoutapp.user;

import io.github.joenas.workoutapp.user.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {
    UserModel findByOauthId(String oauthId);
    void deleteByOauthId(String oauthId);
}
