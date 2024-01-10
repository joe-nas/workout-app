package io.github.joenas.workoutapp;

import com.github.javafaker.Faker;
import io.github.joenas.workoutapp.user.model.OauthDetails;
import io.github.joenas.workoutapp.user.User;
import io.github.joenas.workoutapp.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class WorkoutAppApplication{

    public static void main(String[] args) {
        SpringApplication.run(WorkoutAppApplication.class, args);

    }
}
