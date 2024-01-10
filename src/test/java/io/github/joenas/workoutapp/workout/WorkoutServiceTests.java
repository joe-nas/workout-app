package io.github.joenas.workoutapp.workout;

import io.github.joenas.workoutapp.user.User;
import io.github.joenas.workoutapp.user.UserRepository;
import io.github.joenas.workoutapp.user.exceptions.UserNotFoundException;
import io.github.joenas.workoutapp.user.model.Metric;
import io.github.joenas.workoutapp.user.model.UserRoles;
import io.github.joenas.workoutapp.workout.impl.WorkoutServiceImpl;
import io.github.joenas.workoutapp.workout.model.Exercise;
import io.github.joenas.workoutapp.workout.model.Workout;
import io.github.joenas.workoutapp.workout.model.WorkoutSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WorkoutServiceTests {

    @Mock
    UserRepository userRepository;
    @Mock
    WorkoutRepository workoutRepository;

    @InjectMocks
    WorkoutServiceImpl workoutService;

    Workout workout;
    User user;

    List<Workout> workouts;

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
                .oauthId("12345678")
                .dateCreated(new Date())
                .dateModified(new Date())
                .workoutName("Chest Day")
                .exercises(List.of(exercise1, exercise2))
                .build();
        Workout workout1 = Workout.builder()
                .id("12345679")
                .oauthId("12345678")
                .dateCreated(new Date())
                .dateModified(new Date())
                .workoutName("back Day")
                .exercises(List.of(exercise1, exercise2))
                .build();

        workouts = List.of(workout, workout1);
    }


    @DisplayName("Test for saveWorkout method")
    @Test
    public void givenWorkoutAndOauthId_whenSaveWorkout_thenReturnWorkout() {
        //given - precondition or setup
        given(userRepository.findByOauthId(user.getOauthId())).willReturn(user);
        workout.setUser(user);
        given(workoutRepository.save(workout)).willReturn(workout);
        //when - action or the behaviour to test
        Workout savedWorkout = workoutService.saveWorkout(workout, user.getOauthId());

        //then - verify the output
        verify(workoutRepository).save(workout);
        assertThat(savedWorkout.getUser()).isEqualTo(user);
        assertEquals(savedWorkout, workout);
    }

    @DisplayName("Test for saveWorkout when User not found")
    @Test
    public void givenWorkoutAndOauthId_whenSaveWorkout_thenThrowUserNotFoundException() {
        //given - precondition or setup
        when(userRepository.findByOauthId(user.getOauthId())).thenReturn(null);
        //when - action or the behaviour to test
        assertThrows(UserNotFoundException.class, () -> {
            workoutService.saveWorkout(workout, user.getOauthId());
        });
        //then - verify the output
        verify(userRepository).findByOauthId(user.getOauthId());
    }


    @DisplayName("Test for retrieving workouts by oauthId")
    @Test
    public void givenOauthId_whenFindByOauthId_thenReturnWorkoutList() {
        //given - precondition or setup
        given(workoutRepository.findByOauthId(user.getOauthId())).willReturn(workouts);
        //when - action or the behaviour to test
        List<Workout> workoutList = workoutService.findWorkoutsByOauthId(user.getOauthId());

        //then - verify the output
        verify(workoutRepository).findByOauthId(user.getOauthId());
        assertThat(workoutList).isNotNull();
        assertThat(workoutList.size()).isEqualTo(2);
        assertThat(workoutList).isEqualTo(workouts);
    }

    @DisplayName("Test for retrieving workouts by oauthId when no workouts found")
    @Test
    public void givenOauthId_whenFindByOauthId_thenReturnEmptyWorkoutList() {
        //given - precondition or setup
        given(workoutRepository.findByOauthId(user.getOauthId())).willReturn(List.of());
        //when - action or the behaviour to test
        List<Workout> workoutList = workoutService.findWorkoutsByOauthId(user.getOauthId());

        //then - verify the output
        verify(workoutRepository).findByOauthId(user.getOauthId());
        assertThat(workoutList).isEmpty();
    }

}
