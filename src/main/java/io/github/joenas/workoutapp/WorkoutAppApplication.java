package io.github.joenas.workoutapp;

import com.github.javafaker.Faker;
import io.github.joenas.workoutapp.user.model.OauthDetails;
import io.github.joenas.workoutapp.user.UserModel;
import io.github.joenas.workoutapp.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class WorkoutAppApplication{

    Faker faker = new Faker();
    UserRepository userRepository;

    public WorkoutAppApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(WorkoutAppApplication.class, args);

    }

    @Component
    public class createUserCommandLineRunner implements CommandLineRunner {

        @Override
        public void run(String... args) {
            UserModel user = new UserModel(faker.funnyName().name(),
                    faker.internet().emailAddress(),
                    faker.internet().uuid(),
                    new OauthDetails("google",faker.number().toString()));
            userRepository.save(user);
        }
    }
}
