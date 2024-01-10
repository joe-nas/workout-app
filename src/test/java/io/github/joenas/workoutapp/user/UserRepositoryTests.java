package io.github.joenas.workoutapp.user;

import io.github.joenas.workoutapp.MongoDbTestConfig;
import io.github.joenas.workoutapp.user.model.Metric;
import io.github.joenas.workoutapp.user.model.UserRoles;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.github.joenas.workoutapp.MongoDbTestConfig.mongoDBContainer;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@DataMongoTest
@Testcontainers
@ContextConfiguration(classes = MongoDbTestConfig.class)
public class UserRepositoryTests {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @BeforeAll
    public static void checkContainer() {
        Assertions.assertTrue(mongoDBContainer.isRunning(), "MongoDB container should be running");
    }

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
    }

    @AfterEach
    public void tearDown() {

        mongoTemplate.dropCollection(User.class);
    }

    @DisplayName("Integration test for saving new user")
    @Test
    public void givenUserObject_whenSave_thenReturnSavedEmployee() {
        //given - precondition or setup
        // setup

        //when - action or the behaviour to test
        User savedUser = userRepository.save(user);

        //then - verify the output
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId().length()).isGreaterThan(0);
    }

    @DisplayName("Integration test for finding Employee by OauthId")
    @Test
    public void givenEmployeeList_whenFindByOauthId_thenReturnUser() {
        //given - precondition or setup
        User savedUser = userRepository.save(user);

        //when - action or the behaviour to test
        User foundUser = userRepository.findByOauthId(savedUser.getOauthId());
        //then - verify the output
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getOauthId()).isEqualTo(savedUser.getOauthId());
    }

    @DisplayName("Integration test for findAll operation")
    @Test
    public void givenEmployeeList_whenFindAll_thenReturnEmployeeListObject() {
        //given - precondition or setup
        userRepository.save(user);
        User user1 = User.builder()
                .username("JaneDoe")
                .email("jane@doe.com")
                .oauthId("123456789")
                .userRoles(List.of(UserRoles.USER))
                .metric(Metric.KG)
                .build();
        userRepository.save(user1);

        //when - action or the behaviour to test
        List<User> userListDb = userRepository.findAll();

        //then - verify the output
        assertThat(userListDb).isNotNull();
        assertThat(userListDb).hasSize(2);
        assertThat(userListDb.get(0).getUsername()).isEqualTo(user.getUsername());
        assertThat(userListDb.get(1).getUsername()).isEqualTo(user1.getUsername());
        assertThat(userListDb.get(0).getId().length()).isGreaterThan(0);
        assertThat(userListDb.get(1).getId().length()).isGreaterThan(0);
    }


    @DisplayName("Integration test for update operation")
    @Test
    public void givenUserObject_whenUpdateUser_thenReturnUpdatedUser() {
        //given - precondition or setup
        userRepository.save(user);
        //when - action or the behaviour to test
        User userToUpdate = userRepository.findByOauthId(user.getOauthId());
        userToUpdate.setUsername("JaneDoe");
        userToUpdate.setEmail("jane@doe.com");
        userToUpdate.setMetric(Metric.LBS);
        User updatedUser = userRepository.save(userToUpdate);
        //then - verify the output
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getUsername()).isEqualTo(userToUpdate.getUsername());
        assertThat(updatedUser.getEmail()).isEqualTo(userToUpdate.getEmail());
        assertThat(updatedUser.getMetric()).isEqualTo(userToUpdate.getMetric());
    }


    @DisplayName("Integration test for deleteById operation")
    @Test
    public void givenUserObject_whenDeleteById_thenRemoveUser() {
        //given setup
        userRepository.save(user);
        //when
        userRepository.deleteById(user.getId());
        Optional<User> userOptional = userRepository.findById(user.getId());
        //then
        assertThat(userOptional).isEmpty();
    }

    @DisplayName("Integration test for deleteByOauthId method")
    @Test
    public void givenOauthId_whenDeleteByOauthId_thenReturnVoid() {
        //given - precondition or setup
        userRepository.save(user);
        //when - action or the behaviour to test
        userRepository.deleteByOauthId(user.getOauthId());
        Optional<User> userOptional = userRepository.findById(user.getId());
        //then - verify the output
        assertThat(userOptional).isEmpty();
    }
}
