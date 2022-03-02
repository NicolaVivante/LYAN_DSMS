package it.castelli.lyan;

import it.castelli.encryption.RSA;
import it.castelli.encryption.SHA_256;

import java.security.PrivateKey;
import java.security.PublicKey;

public class SignatureManager {

    public static String getSignature(String content, PrivateKey privateKey) {
        String contentDigest = SHA_256.getDigest(content);
        return RSA.encrypt(contentDigest, privateKey);
    }

    // TODO: change publicKey into certificate
    public static boolean verifySignature(String content, String signature, PublicKey publicKey) {
        String calculatedDigest = SHA_256.getDigest(content);
        String receivedDigest = RSA.decrypt(signature, publicKey);
        return calculatedDigest.equals(receivedDigest);
    }
}
