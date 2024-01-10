package io.github.joenas.workoutapp.user;


import io.github.joenas.workoutapp.user.impl.UserServiceImpl;
import io.github.joenas.workoutapp.user.model.Metric;
import io.github.joenas.workoutapp.user.model.UserRoles;
import io.github.joenas.workoutapp.workout.WorkoutRepository;
import io.github.joenas.workoutapp.workout.model.Exercise;
import io.github.joenas.workoutapp.workout.model.Workout;
import io.github.joenas.workoutapp.workout.model.WorkoutSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;


import static com.mongodb.assertions.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WorkoutRepository workoutRepository;
    @Mock
    private MongoTemplate mongoTemplate;
    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private Workout workout;
    private Exercise exercise;
    private List<WorkoutSet> workoutSet;

    @BeforeEach
    public void setup() {
        List<UserRoles> roles = List.of(UserRoles.USER);
        user = User.builder()
                .username("JohnDoe")
                .email("john@doe.com")
                .userRoles(roles)
                .oauthId("12345678")
                .metric(Metric.KG)
                .build();

        workoutSet = List.of(
                WorkoutSet.builder()
                        .reps(10)
                        .weight(100)
                        .rpe(7)
                        .build(),
                WorkoutSet.builder()
                        .reps(10)
                        .weight(110)
                        .rpe(8)
                        .build()
        );

        exercise = Exercise.builder()
                .exerciseName("Bench press")
                .sets(workoutSet)
                .build();

        workout = Workout.builder()
                .workoutName("Test workout")
                .dateCreated(new Date())
                .oauthId("12345678")
                .user(user)
                .exercises(List.of(exercise))
                .build();
    }

    @DisplayName("Unit test for updateUser operation - positive scenario")
    @Test
    void givenUser_whenUpdateUser_thenReturnUpdatedExistingUser() {
        //given
        Query query = new Query();
        query.addCriteria(Criteria.where("oauthId").is(user.getOauthId()));

        Update update = new Update();
        update.set("username", user.getUsername());
        update.set("email", user.getEmail());
        update.set("profilePictureUrl", user.getProfilePictureUrl());
        update.set("metric", user.getMetric());

        //when
        when(mongoTemplate.findAndModify(query, update, User.class)).thenReturn(user);
        User updatedUser = userService.updateUser(user, user.getOauthId());
        //then
        assertEquals(user, updatedUser);
    }

    @DisplayName("Unit test for updateUser operation - negative scenario")
    @Test
    void givenInvalidUser_whenUpdateUser_thenReturnVoid() {
        //given
        Query query = new Query();
        query.addCriteria(Criteria.where("oauthId").is(user.getOauthId()));

        Update update = new Update();
        update.set("username", user.getUsername());
        update.set("email", user.getEmail());
        update.set("profilePictureUrl", user.getProfilePictureUrl());
        update.set("metric", user.getMetric());

        //when
        when(mongoTemplate.findAndModify(query, update, User.class)).thenReturn(null);

        User updatedUser = userService.updateUser(user, user.getOauthId());

        //then
        assertNull(updatedUser);
    }
}
