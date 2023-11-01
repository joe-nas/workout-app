package io.github.joenas.workoutapp.repository;

import io.github.joenas.workoutapp.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByOauthId(String oauthId);
}
