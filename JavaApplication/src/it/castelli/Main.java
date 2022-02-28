package it.castelli;

import it.castelli.encryption.AES;
import it.castelli.lyan.SignedFile;
import it.castelli.utils.Compressor;
import it.castelli.encryption.RSA;
import it.castelli.encryption.SHA_256;

import java.io.File;
import java.security.KeyPair;

public class Main {

    public static void testDigest(String message) {
        String digest = SHA_256.getDigest(message);
        System.out.println("Digest: " + digest);
    }

    public static void testAES(String message, String key) {
        String cipherText = AES.encrypt(message, key);
        System.out.println("AES encrypted message: " + cipherText);
        String plainText = AES.decrypt(cipherText, key);
        System.out.println("AES decrypted message: " + plainText);
    }

    public static void testRSA(String message) {
        KeyPair keyPair = RSA.generateKeyPair();

        String cipherText = RSA.encrypt(message, keyPair.getPrivate());
        System.out.println("RSA encrypted message (private): " + cipherText);
        String plainText = RSA.decrypt(cipherText, keyPair.getPublic());
        System.out.println("RSA decrypted message (public): " + plainText);

        cipherText = RSA.encrypt(message, keyPair.getPublic());
        System.out.println("RSA encrypted message (public): " + cipherText);
        plainText = RSA.decrypt(cipherText, keyPair.getPrivate());
        System.out.println("RSA decrypted message (private): " + plainText);
    }

    public static void testCompression(String message) {
        String compressedText = Compressor.compress(message);
        System.out.println("Compressed message: ");
        System.out.println(compressedText);
    }

    public static void main(String[] args) {
        String message = "a very secret message, do not let others than you read this";
        String key = "secret key";
        KeyPair keyPair = RSA.generateKeyPair();
        File newFile = new File("C:\\Users\\Win10\\Documents\\GitHub\\LYAN_DSMS\\JavaApplication\\src\\main\\resources\\amogus.txt");
        try {
            SignedFile signedFile = SignedFile.createSignedFile(newFile, keyPair.getPrivate());
            signedFile.toFile("C:\\Users\\Win10\\Documents\\GitHub\\LYAN_DSMS\\JavaApplication\\src\\main\\resources\\");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Original message: " + message);

//        testDigest(message);
//        testAES(message, key);
//        testRSA(message);
//        testCompression(message);
    }
}
