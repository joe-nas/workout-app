package io.github.joenas.workoutapp.user;


public interface UserService {
    User updateUser(User user, String oauthId);
    User findUserByOauthId(String oauthId);
    User saveUser(User user);
}