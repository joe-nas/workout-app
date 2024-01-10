package io.github.joenas.workoutapp.workout;

import io.github.joenas.workoutapp.MongoDbTestConfig;
import io.github.joenas.workoutapp.user.User;
import io.github.joenas.workoutapp.user.UserRepository;
import io.github.joenas.workoutapp.user.model.Metric;
import io.github.joenas.workoutapp.user.model.UserRoles;
import io.github.joenas.workoutapp.workout.model.Exercise;
import io.github.joenas.workoutapp.workout.model.Workout;
import io.github.joenas.workoutapp.workout.model.WorkoutSet;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static io.github.joenas.workoutapp.MongoDbTestConfig.mongoDBContainer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@Testcontainers
@ContextConfiguration(classes = MongoDbTestConfig.class)
public class WorkoutRepositoryTests {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private WorkoutRepository workoutRepository;

    private Workout workout;
    private User user;
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @BeforeAll
    public static void checkContainer() {
        assertTrue(mongoDBContainer.isRunning(), "MongoDB container should be running");
    }

    @BeforeEach
    public void setup() {
        List<UserRoles> roles = List.of(UserRoles.USER);
        user = User.builder()
                .id("12345678")
                .username("JohnDoe")
                .email("john@doe.com")
                .userRoles(roles)
                .oauthId("12345678")
                .metric(Metric.KG)
                .build();

        WorkoutSet ws1 = WorkoutSet.builder()
                .setNumber(1)
                .reps(8)
                .weight(85)
                .rpe(9)
                .build();
        WorkoutSet ws2 = WorkoutSet.builder()
                .setNumber(2)
                .reps(8)
                .weight(85)
                .rpe(9)
                .build();

        Exercise exercise1 = Exercise.builder()
                .exerciseName("Bench Press")
                .sets(List.of(ws1, ws2))
                .build();

        Exercise exercise2 = Exercise.builder()
                .exerciseName("Incline Bench Press")
                .sets(List.of(ws1, ws2))
                .build();

        workout = Workout.builder()
                .id("12345678")
                .oauthId(user.getOauthId())
                .dateCreated(new Date())
                .dateModified(new Date())
                .workoutName("Chest Day")
                .user(user)
                .exercises(List.of(exercise1,exercise2))
                .build();
    }

    @AfterEach
    public void tearDown() {

        mongoTemplate.dropCollection(Workout.class);
        mongoTemplate.dropCollection(User.class);
    }

    @DisplayName("Test for saving workout")
    @Test
    public void givenWorkout_whenSave_thenReturnSavedWorkout() {
        //given - precondition or setup
        // setup

        //when - action or the behaviour to test
        Workout savedWorkout = workoutRepository.save(workout);

        //then - verify the output
        assertThat(savedWorkout).isNotNull();
        assertThat(savedWorkout.getId().length()).isGreaterThan(0);
        assertThat(savedWorkout.getExercises().size()).isEqualTo(2);
        assertThat(savedWorkout.getExercises().get(0).getSets().size()).isEqualTo(2);
        assertThat(savedWorkout.getExercises().get(1).getSets().size()).isEqualTo(2);
        assertThat(savedWorkout.getUser().getId()).isEqualTo(user.getId());
    }

    @DisplayName("Test for finding workout by oauthId")
    @Test
    public void givenOauthId_whenFindByOauthId_thenReturnWorkoutList() {
        //given - precondition or setup
        Workout savedWorkout = workoutRepository.save(workout);
        //when - action or the behaviour to test
        List<Workout> workoutList = workoutRepository.findByOauthId(user.getOauthId());
        //then - verify the output
        assertThat(workoutList).isNotNull();
        assertThat(workoutList.size()).isEqualTo(1);
    }

    @DisplayName("Test for deleting workout by id")
    @Test
    public void givenWorkoutId_whenDeleteWorkoutById_thenRemoveWorkout() {
        //given - precondition or setup
        Workout savedWorkout = workoutRepository.save(workout);

        //when - action or the behaviour to test
        workoutRepository.deleteById(savedWorkout.getId());
        Optional<Workout> workoutDb = workoutRepository.findById(savedWorkout.getId());

        //then - verify the output
        assertThat(workoutDb).isEmpty();
    }
}
