package io.github.joenas.workoutapp.user;


import io.github.joenas.workoutapp.user.impl.UserServiceImpl;
import io.github.joenas.workoutapp.user.model.Metric;
import io.github.joenas.workoutapp.user.model.UserRoles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.anyString;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserModel user;

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
    }


}
