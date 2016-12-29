package cj1098.event;

/**
 * Created by chris on 12/27/16.
 */

public class AccessTokenRetrievedEvent extends BaseEvent {
    private String accessToken;
    private int expiresIn;

    public AccessTokenRetrievedEvent(String accessToken, int expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }
}
