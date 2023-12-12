package io.github.joenas.workoutapp.user;


import io.github.joenas.workoutapp.user.impl.UserServiceImpl;
import io.github.joenas.workoutapp.user.model.Metric;
import io.github.joenas.workoutapp.user.model.UserRoles;
import io.github.joenas.workoutapp.workout.WorkoutRepository;
import io.github.joenas.workoutapp.workout.model.ExerciseModel;
import io.github.joenas.workoutapp.workout.model.WorkoutModel;
import io.github.joenas.workoutapp.workout.model.WorkoutSetModel;
import org.assertj.core.api.Assertions;
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


import javax.swing.text.html.Option;

import static com.mongodb.assertions.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    private UserModel user;
    private WorkoutModel workout;
    private ExerciseModel exercise;
    private List<WorkoutSetModel> workoutSet;

    @BeforeEach
    public void setup() {
        List<UserRoles> roles = List.of(UserRoles.USER);
        user = UserModel.builder()
                .username("JohnDoe")
                .email("john@doe.com")
                .userRoles(roles)
                .oauthId("12345678")
                .metric(Metric.KG)
                .build();

        workoutSet = List.of(
                WorkoutSetModel.builder()
                        .reps(10)
                        .weight(100)
                        .rpe(7)
                        .build(),
                WorkoutSetModel.builder()
                        .reps(10)
                        .weight(110)
                        .rpe(8)
                        .build()
        );

        exercise = ExerciseModel.builder()
                .exerciseName("Bench press")
                .sets(workoutSet)
                .build();

        workout = WorkoutModel.builder()
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
        when(mongoTemplate.findAndModify(query, update, UserModel.class)).thenReturn(user);
        UserModel updatedUser = userService.updateUser(user, user.getOauthId());
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
        when(mongoTemplate.findAndModify(query, update, UserModel.class)).thenReturn(null);

        UserModel updatedUser = userService.updateUser(user, user.getOauthId());

        //then
        assertNull(updatedUser);
    }

    @DisplayName("Unit test for findWorkoutsByOauthId operation - positive scenario")
    @Test
    public void givenOauthId_whenFindWorkoutsByOauthId_thenReturnWorkoutList() {
        //given - precondition or setup
        given(workoutRepository.findByOauthId(user.getOauthId())).willReturn(List.of(workout));
        //when - action or the behaviour to test
        List<WorkoutModel> workoutList = userService.findWorkoutsByOauthId(user.getOauthId());
        //then - verify the output
        Assertions.assertThat(workoutList).isNotNull();
        Assertions.assertThat(workoutList.get(0).getOauthId()).isEqualTo(user.getOauthId());
    }
}
