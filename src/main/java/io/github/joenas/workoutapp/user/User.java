package io.github.joenas.workoutapp.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.net.URL;
import java.util.List;

@Document(collection = "users")
@Getter @Setter @NoArgsConstructor @ToString
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

    public User(String username, String email, String oauthId) {

        this.username = username;
        this.email = email;
        this.userRoles = List.of(UserRoles.USER);
        this.oauthId = oauthId;
        this.metric = Metric.KG;
    }

    //    private Set<UserRoles> userRoles;
//
//    private PersonalInformation personalInformation;
//
//    private Set<String> exerciseNames;
//
//    // Oauth providers
//    private OauthInfo googleOauth;
//    private OauthInfo facebookOauth;
//    private OauthInfo twitterOauth;
//    private OauthInfo githubOauth;
//
//    public User() {
//    }
//
//    public User(String username) {
//        this.username = username;
//    }
//
//    public User(String userID, String username, Set<UserRoles> userRoles, PersonalInformation personalInformation, Set<String> exerciseNames, OauthInfo googleOauth, OauthInfo facebookOauth, OauthInfo twitterOauth, OauthInfo githubOauth) {
//        this.userID = userID;
//        this.username = username;
//        this.userRoles = userRoles;
//        this.personalInformation = personalInformation;
//        this.exerciseNames = exerciseNames;
//        this.googleOauth = googleOauth;
//        this.facebookOauth = facebookOauth;
//        this.twitterOauth = twitterOauth;
//        this.githubOauth = githubOauth;
//    }
//
//    public String getUserID() {
//        return userID;
//    }
//
//    public void setUserID(String userID) {
//        this.userID = userID;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public Set<UserRoles> getUserRoles() {
//        return userRoles;
//    }
//
//    public void setUserRoles(Set<UserRoles> userRoles) {
//        this.userRoles = userRoles;
//    }
//
//    public PersonalInformation getPersonalInformation() {
//        return personalInformation;
//    }
//
//    public void setPersonalInformation(PersonalInformation personalInformation) {
//        this.personalInformation = personalInformation;
//    }
//
//    public Set<String> getExerciseNames() {
//        return exerciseNames;
//    }
//
//    public void setExerciseNames(Set<String> exerciseNames) {
//        this.exerciseNames = exerciseNames;
//    }
//
//    public OauthInfo getGoogleOauth() {
//        return googleOauth;
//    }
//
//    public void setGoogleOauth(OauthInfo googleOauth) {
//        this.googleOauth = googleOauth;
//    }
//
//    public OauthInfo getFacebookOauth() {
//        return facebookOauth;
//    }
//
//    public void setFacebookOauth(OauthInfo facebookOauth) {
//        this.facebookOauth = facebookOauth;
//    }
//
//    public OauthInfo getTwitterOauth() {
//        return twitterOauth;
//    }
//
//    public void setTwitterOauth(OauthInfo twitterOauth) {
//        this.twitterOauth = twitterOauth;
//    }
//
//    public OauthInfo getGithubOauth() {
//        return githubOauth;
//    }
//
//    public void setGithubOauth(OauthInfo githubOauth) {
//        this.githubOauth = githubOauth;
//    }
//
//    @Override
//    public String toString() {
//        return "User{" +
//                "userID=" + userID +
//                ", username='" + username + '\'' +
//                ", userRoles=" + userRoles +
//                ", personalInformation=" + personalInformation +
//                ", exerciseNames=" + exerciseNames +
//                ", googleOauth=" + googleOauth +
//                ", facebookOauth=" + facebookOauth +
//                ", twitterOauth=" + twitterOauth +
//                ", githubOauth=" + githubOauth +
//                '}';
//    }
}