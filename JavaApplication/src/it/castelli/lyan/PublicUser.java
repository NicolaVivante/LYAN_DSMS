package it.castelli.lyan;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.security.PublicKey;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PublicUser {

    private final String userName;
    private final PublicKey publicKey;

    public PublicUser(String userName, PublicKey publicKey) {
        this.userName = userName;
        this.publicKey = publicKey;
    }

    public String getUserName() {
        return userName;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}
