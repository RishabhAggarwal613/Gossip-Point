package com.gossip_point.app.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private boolean success;
    private String message;

    public AuthResponse(String messageOrToken, boolean success, boolean isToken) {
        this.success = success;
        if (isToken) {
            this.token = messageOrToken;
        } else {
            this.message = messageOrToken;
        }
    }
}