package it.castelli.encryption;

import it.castelli.Converter;

import java.security.MessageDigest;

public class SHA_256 {

    private final static String HASH_ALGORITHM = "SHA-256";

    public static String getDigest(String message) {
        byte[] messageBytes = Converter.stringToByteArray(message);
        byte[] digest = getDigest(messageBytes);
        return Converter.byteArrayToString(digest);
    }

    public static byte[] getDigest(byte[] messageBytes) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            return messageDigest.digest(messageBytes);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
