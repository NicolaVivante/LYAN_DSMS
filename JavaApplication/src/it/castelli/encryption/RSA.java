package it.castelli.encryption;

import it.castelli.utils.Converter;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class RSA {

    public static KeyPair generateKeyPair(){
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            return generator.generateKeyPair();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] encrypt(byte[] plainTextBytes, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            return cipher.doFinal(plainTextBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] decrypt(byte[] cipherTextBytes, Key key) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(cipherTextBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String plainText, Key key) {
        byte[] plainTextBytes = Converter.stringToByteArray(plainText);
        byte[] cipherTextBytes = encrypt(plainTextBytes, key);
        return Converter.byteArrayToString(cipherTextBytes);
    }

    public static String decrypt(String cipherText, Key key) {
        byte[] cipherTextBytes = Converter.stringToByteArray(cipherText);
        byte[] plainTextBytes = decrypt(cipherTextBytes, key);
        return Converter.byteArrayToString(plainTextBytes);
    }
}