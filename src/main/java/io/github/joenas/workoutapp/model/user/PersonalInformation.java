package io.github.joenas.workoutapp.model.user;
enum PreferredUnit {
    METRIC, IMPERIAL
}

public class PersonalInformation {
    private String email;
    private String profilePictureUrl;
    private PreferredUnit preferredUnit;

    public PersonalInformation() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public PreferredUnit getPreferredUnit() {
        return preferredUnit;
    }

    public void setPreferredUnit(PreferredUnit preferredUnit) {
        this.preferredUnit = preferredUnit;
    }

    @Override
    public String toString() {
        return "PersonalInformation{" +
                "email='" + email + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", preferredUnit=" + preferredUnit +
                '}';
    }
}