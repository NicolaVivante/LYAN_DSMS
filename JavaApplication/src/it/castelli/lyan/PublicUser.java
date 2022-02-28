package it.castelli.lyan;

import java.security.PublicKey;

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
