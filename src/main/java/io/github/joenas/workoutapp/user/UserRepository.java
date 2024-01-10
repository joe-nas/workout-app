package io.github.joenas.workoutapp.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByOauthId(String oauthId);
    void deleteByOauthId(String oauthId);
}
