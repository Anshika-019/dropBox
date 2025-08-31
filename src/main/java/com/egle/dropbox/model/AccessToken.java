package com.egle.dropbox.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.Instant;

@Entity
public class AccessToken {

    @Id
    private String teamId;
    private String accessToken;
    private String refreshToken;
    private Instant expiresAt;

    public AccessToken() {}

    public AccessToken(String teamId, String accessToken, String refreshToken, Instant expiresAt) {
        this.teamId = teamId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
    }

    // getters
    public String getTeamId() { return teamId; }
    public String getAccessToken() { return accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public Instant getExpiresAt() { return expiresAt; }

    // setters
    public void setTeamId(String teamId) { this.teamId = teamId; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }
}
