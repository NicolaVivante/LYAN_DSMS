package it.castelli.encryption;

import it.castelli.Converter;
import it.castelli.Extensions;
import it.castelli.Paths;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSA {

    private final static String ALGORITHM = "RSA";

    public static KeyPair generateKeyPair() {
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

    private static byte[] encrypt(byte[] plainTextBytes, Key key) throws Exception {
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

    public static String decrypt(String cipherText, Key key) throws Exception {
        byte[] cipherTextBytes = Converter.stringToByteArray(cipherText);
        byte[] plainTextBytes = decrypt(cipherTextBytes, key);
        return Converter.byteArrayToString(plainTextBytes);
    }

    public static PublicKey publicKeyFromString(String publicKeyString) {
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

    public static void saveKeys(KeyPair keyPair, String name) throws Exception {
        // save private key
        {
            String privateKeyString = privateKeyToString(keyPair.getPrivate());
            byte[] fileContentBytes = Converter.stringToByteArray(privateKeyString);
            String fileName = Paths.KEYS_PATH + name + Extensions.PRIVATE_KEYS_EXTENSION;
            File newFile = new File(fileName);
            if (newFile.createNewFile()) {
                FileOutputStream outputStream = new FileOutputStream(fileName);
                outputStream.write(fileContentBytes);
                outputStream.close();
            } else {
                throw new Exception("File already exists");
            }
        }
        // save public key
        {
            String publicKeyString = publicKeyToString(keyPair.getPublic());
            byte[] fileContentBytes = Converter.stringToByteArray(publicKeyString);
            String fileName = Paths.KEYS_PATH + name + Extensions.PUBLIC_KEYS_EXTENSION;
            File newFile = new File(fileName);
            if (newFile.createNewFile()) {
                FileOutputStream outputStream = new FileOutputStream(fileName);
                outputStream.write(fileContentBytes);
                outputStream.close();
            } else {
                throw new Exception("File already exists");
            }
        }
    }

    public static KeyPair fromFile(File publicKeyFile, File privateKeyFile) throws Exception {
        PublicKey publicKey;
        PrivateKey privateKey;

        // get public key
        byte[] fileContentBytes = Files.readAllBytes(publicKeyFile.toPath());
        String fileContent = Converter.byteArrayToString(fileContentBytes);
        publicKey = publicKeyFromString(fileContent);

        // get private key
        fileContentBytes = Files.readAllBytes(privateKeyFile.toPath());
        fileContent = Converter.byteArrayToString(fileContentBytes);
        privateKey = privateKeyFromString(fileContent);

        // compose key pair
        return new KeyPair(publicKey, privateKey);
    }
}