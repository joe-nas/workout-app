package io.github.joenas.workoutapp.model.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.net.URL;
import java.util.List;

@Document(collection = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User{

    @Id
    private @MongoId String id;
    @Indexed(unique = true)
    private String oauthId;
    private List<UserRoles> userRoles;
    private String username;
    private String email;
    private Metric metric;
    private URL profilePictureUrl;
    private OauthDetails oauthDetails;

    public User(String username, String email, String oauthId, OauthDetails oauthDetails) {

        this.username = username;
        this.email = email;
        this.userRoles = List.of(UserRoles.USER);
        this.oauthId = oauthId;
        this.metric = Metric.KG;
        this.oauthDetails = oauthDetails;
    }
}