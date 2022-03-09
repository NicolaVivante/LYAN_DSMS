package it.castelli.lyan;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.castelli.encryption.RSA;

import java.security.PublicKey;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class PublicUser {

    private String userName;
    private String publicKeyString;

    public PublicUser(String userName, PublicKey publicKey) {
        this.userName = userName;
        publicKeyString = RSA.publicKeyToString(publicKey);
    }

    /**
     * Constructor for ObjectMapper
     */
    private PublicUser() {}

    /**
     * @return The username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return The public key
     */
    @JsonIgnore
    public PublicKey getPublicKey() {
        return RSA.publicKeyFromString(publicKeyString);
    }

    @JsonIgnore
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PublicUser) {
            return this.userName.equals(((PublicUser) obj).userName) &&
                    this.publicKeyString.equals(((PublicUser) obj).publicKeyString);
        }
        return false;
    }
}
