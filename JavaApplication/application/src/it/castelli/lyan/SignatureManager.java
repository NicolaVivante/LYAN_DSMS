package it.castelli.lyan;

import it.castelli.encryption.RSA;
import it.castelli.encryption.SHA_256;

import java.security.PrivateKey;
import java.security.PublicKey;

public class SignatureManager {

    public static String getSignature(String content, PrivateKey privateKey) throws Exception{
        try {
            String contentDigest = SHA_256.getDigest(content);
            return RSA.encrypt(contentDigest, privateKey);
        } catch (Exception e) {
            throw new Exception("Encryption error");
        }
    }

    public static boolean verifySignature(String content, String signature, Certifier.Certificate certificate) throws Exception{
            String calculatedDigest = SHA_256.getDigest(content);
            if (!Certifier.isValid(certificate)) throw new Exception("Invalid certificate!");
            PublicKey publicKey = certificate.getPublicUser().getPublicKey();
            try {
                String receivedDigest = RSA.decrypt(signature, publicKey);
                return calculatedDigest.equals(receivedDigest);
            } catch (Exception e) {
                //TODO: remove print
                e.printStackTrace();
                return false;
            }
    }
}
