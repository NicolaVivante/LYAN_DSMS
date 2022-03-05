package it.castelli.encryption;

import it.castelli.utils.Converter;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSA {

    private final static String ALGORITHM = "RSA";

    public static KeyPair generateKeyPair(){
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM);
            generator.initialize(2048);
            return generator.generateKeyPair();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] encrypt(byte[] plainTextBytes, Key key)  throws Exception {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            return cipher.doFinal(plainTextBytes);
    }

    private static byte[] decrypt(byte[] cipherTextBytes, Key key) throws Exception {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(cipherTextBytes);
    }

    public static String encrypt(String plainText, Key key) throws Exception {
        byte[] plainTextBytes = Converter.stringToByteArray(plainText);
        byte[] cipherTextBytes = encrypt(plainTextBytes, key);
        return Converter.byteArrayToString(cipherTextBytes);
    }

    public static String decrypt(String cipherText, Key key)  throws Exception {
        byte[] cipherTextBytes = Converter.stringToByteArray(cipherText);
        byte[] plainTextBytes = decrypt(cipherTextBytes, key);
        return Converter.byteArrayToString(plainTextBytes);
    }

    public static PublicKey publicKeyFromString(String publicKeyString){
        try {
            byte[] publicKeyBytes = Converter.stringToByteArray(publicKeyString);
            KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
            return kf.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PrivateKey privateKeyFromString(String privateKeyString) {
        try {
            byte[] privateKeyBytes = Converter.stringToByteArray(privateKeyString);
            KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
            return kf.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String privateKeyToString(PrivateKey privateKey) {
        byte[] privateKeyBytes = privateKey.getEncoded();
        return Converter.byteArrayToString(privateKeyBytes);
    }

    public static String publicKeyToString(PublicKey publicKey) {
        byte[] publicKeyBytes = publicKey.getEncoded();
        return Converter.byteArrayToString(publicKeyBytes);
    }
}