package cj1098.animeshare.userList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by chris on 12/27/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessTokenInformation {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private int expiresIn;
    private long expires;

    public AccessTokenInformation() {
    }

    public AccessTokenInformation(String accessToken, String tokenType, int expiresIn, long expires) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.expires = expires;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public long getExpires() {
        return expires;
    }
}
