package io.github.joenas.workoutapp.user;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.joenas.workoutapp.config.CustomJwtAuthenticationFilter;
import io.github.joenas.workoutapp.config.JwtDecoderConf;
import io.github.joenas.workoutapp.config.Oauth2SecurityConfiguration;
import io.github.joenas.workoutapp.user.model.Metric;
import io.github.joenas.workoutapp.user.model.OauthDetails;
import io.github.joenas.workoutapp.user.model.UserRoles;
import io.github.joenas.workoutapp.workout.WorkoutService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

@WebMvcTest(UserController.class)
@Import({Oauth2SecurityConfiguration.class, JwtDecoderConf.class, CustomJwtAuthenticationFilter.class})
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private WorkoutService workoutService;


    private User user;

    @BeforeEach
    public void setup() {
        List<UserRoles> roles = List.of(UserRoles.USER);

        OauthDetails oauthDetails = OauthDetails.builder()
                .oauthProvider("google")
                .oauthId("12345678")
                .build();

        user = User.builder()
                .username("JohnDoe")
                .email("john@doe.com")
                .userRoles(roles)
                .oauthId("12345678")
                .metric(Metric.KG)
                .oauthDetails(oauthDetails)
                .build();
    }


    @DisplayName("Testing /user/create endpoint - positive scenario")
    @Test
    public void givenNewUser_whenSaveUser_thenReturnNewUser() throws Exception {
        //given - precondition or setup
        given(userService.findUserByOauthId(user.getOauthId())).willReturn(null);
        given(userService.saveUser(any(User.class))).
                willAnswer((invocation) -> invocation.getArgument(0));
        //when - action or the behaviour to test
        ResultActions response = mockMvc.perform(post("/api/user/create", user)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));
        //then - verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.userRoles").value(UserRoles.USER.name()))
                .andExpect(jsonPath("$.oauthDetails.oauthId").value(user.getOauthDetails().getOauthId()))
                .andExpect(jsonPath("$.oauthDetails.oauthProvider").value(user.getOauthDetails().getOauthProvider()))
                .andExpect(jsonPath("$.oauthId").value(user.getOauthId()))
                .andExpect(jsonPath("$.metric").value(user.getMetric().toString()));

        verify(userService, times(1)).findUserByOauthId(user.getOauthId());
        verify(userService, times(1)).saveUser(any(User.class));
    }

    @DisplayName("Testing /user/create endpoint - negative scenario")
    @Test
    public void givenExisingUser_whenSaveUser_thenHttpStatusConflict() throws Exception {
        //given - precondition or setup
        given(userService.findUserByOauthId(user.getOauthId())).willReturn(user);

        //when - action or the behaviour to test
        ResultActions response = mockMvc.perform(post("/api/user/create", user)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));
        //then - verify the output
        response.andDo(print())
                .andExpect(status().isConflict());

        verify(userService, times(1)).findUserByOauthId(user.getOauthId());
    }


    @DisplayName("Testing /user/{oauthId} endpoint - positive scenario")
    @Test
    public void givenOauthId_whenRetrieveUserByOauthId_thenReturnUserObject() throws Exception {
        //given - precondition or setup
        given(userService.findUserByOauthId(user.getOauthId()))
                .willReturn(user);
        //when - action or the behaviour to test
        ResultActions response = mockMvc.perform(get("/api/user/{oauthId}", user.getOauthId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));
        //then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.userRoles").value(UserRoles.USER.name()))
                .andExpect(jsonPath("$.oauthDetails.oauthId").value(user.getOauthDetails().getOauthId()))
                .andExpect(jsonPath("$.oauthDetails.oauthProvider").value(user.getOauthDetails().getOauthProvider()))
                .andExpect(jsonPath("$.oauthId").value(user.getOauthId()))
                .andExpect(jsonPath("$.metric").value(user.getMetric().toString()));

        verify(userService, times(1)).findUserByOauthId(user.getOauthId());
    }

    @DisplayName("Testing /user/{oauthId} endpoint - negative scenario")
    @Test
    public void givenOauthId_whenRetrieveUserByOauthId_thenHttpStatusNotFound() throws Exception {
        //given - precondition or setup
        String oauthId = "invalidOauthId";
        given(userService.findUserByOauthId(oauthId)).willReturn(null);
        //when - action or the behaviour to test
        ResultActions response = mockMvc.perform(get("/api/user/{oauthId}", oauthId));
        //then - verify the output
        response.andDo(print())
                .andExpect(status().isNotFound());
        verify(userService, times(1)).findUserByOauthId(oauthId);
    }
}
