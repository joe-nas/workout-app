package io.github.joenas.workoutapp.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

//    User findOneUserByPersonalInformationEmail(String emailAddress);
    User findByUsername(String username);
    User findByEmail(String email);
    User updateUserByOauthId(String oauthId, User user);
    User findByOauthId(String oauthId);
}
