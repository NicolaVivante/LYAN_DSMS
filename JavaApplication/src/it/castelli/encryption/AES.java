package it.castelli.encryption;

import it.castelli.utils.Converter;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

public class AES {

    private static boolean initDone = false;

    private static void init() {
        Security.setProperty("crypto.policy", "unlimited");
        initDone = true;
    }

    private static byte[] encrypt (byte[] plainTextBytes, byte[] keyBites) {
        if (!initDone) init();

        try {
            byte[] keyDigest = SHA_256.getDigest(keyBites);
            SecretKeySpec secretKey = new SecretKeySpec(keyDigest, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            return cipher.doFinal(plainTextBytes);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] decrypt (byte[] cipherTextBytes, byte[] keyBites) {
        if (!initDone) init();

        try {
            byte[] keyDigest = SHA_256.getDigest(keyBites);
            SecretKeySpec secretKey = new SecretKeySpec(keyDigest, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            return cipher.doFinal(cipherTextBytes);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String plainText, String key) {
        byte[] plainTextBytes = Converter.stringToByteArray(plainText);
        byte[] keyBytes = Converter.stringToByteArray(key);
        byte[] cipherTextBytes = encrypt(plainTextBytes, keyBytes);
        return Converter.byteArrayToString(cipherTextBytes);
    }

    public static String decrypt(String cipherText, String key) {
        byte[] cipherTextBytes = Converter.stringToByteArray(cipherText);
        byte[] keyBytes = Converter.stringToByteArray(key);
        byte[] plainTextBytes = decrypt(cipherTextBytes, keyBytes);
        return Converter.byteArrayToString(plainTextBytes);
    }
}
