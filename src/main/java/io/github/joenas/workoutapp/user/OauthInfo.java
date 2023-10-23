package io.github.joenas.workoutapp.user;

public class OauthInfo {

    private String oauthProvider;
    private String oauthId;

    public OauthInfo() {
    }

    public OauthInfo(String oauthProvider, String oauthId) {
        this.oauthProvider = oauthProvider;
        this.oauthId = oauthId;
    }

    public String getOauthProvider() {
        return oauthProvider;
    }

    public void setOauthProvider(String oauthProvider) {
        this.oauthProvider = oauthProvider;
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }

    @Override
    public String toString() {
        return "OauthInfo{" +
                "oauthProvider='" + oauthProvider + '\'' +
                ", oauthId='" + oauthId + '\'' +
                '}';
    }
}
