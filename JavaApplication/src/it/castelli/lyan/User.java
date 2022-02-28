package it.castelli.lyan;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class User {

    private final PublicUser publicUser;
    private final PrivateKey privateKey;

    public User(String userName, KeyPair keyPair) {
        publicUser = new PublicUser(userName, keyPair.getPublic());
        privateKey = keyPair.getPrivate();
    }

    public PublicUser getPublicUser() {
        return publicUser;
    }

    public String getUserName() {
        return publicUser.getUserName();
    }

    public PublicKey getPublicKey() {
        return publicUser.getPublicKey();
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
