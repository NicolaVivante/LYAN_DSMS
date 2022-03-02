package it.castelli;

import it.castelli.encryption.AES;
import it.castelli.lyan.Certifier;
import it.castelli.lyan.SignedFile;
import it.castelli.lyan.SourceFile;
import it.castelli.lyan.User;
import it.castelli.utils.Compressor;
import it.castelli.encryption.RSA;
import it.castelli.encryption.SHA_256;

import java.io.File;
import java.security.KeyPair;
import java.util.Arrays;

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

//    public static void testRSA(String message) {
//        KeyPair keyPair = RSA.generateKeyPair();
//
//        String cipherText = RSA.encrypt(message, keyPair.getPrivate());
//        System.out.println("RSA encrypted message (private): " + cipherText);
//        String plainText = RSA.decrypt(cipherText, keyPair.getPublic());
//        System.out.println("RSA decrypted message (public): " + plainText);
//
//        cipherText = RSA.encrypt(message, keyPair.getPublic());
//        System.out.println("RSA encrypted message (public): " + cipherText);
//        plainText = RSA.decrypt(cipherText, keyPair.getPrivate());
//        System.out.println("RSA decrypted message (private): " + plainText);
//    }

    public static void testCompression(String message) {
        String compressedText = Compressor.compress(message);
        System.out.println("Compressed message: ");
        System.out.println(compressedText);
    }

//    public static void testSignedFile(KeyPair keyPair) throws Exception {
//        File newFile = new File("C:\\Users\\Win10\\Documents\\GitHub\\LYAN_DSMS\\JavaApplication\\src\\main\\resources\\amogus.txt");
//        SignedFile signedFile = SignedFile.createSignedFile(newFile, keyPair.getPrivate());
//        signedFile.save("C:\\Users\\Win10\\Documents\\GitHub\\LYAN_DSMS\\JavaApplication\\src\\main\\resources\\");
//        newFile = new File("C:\\Users\\Win10\\Documents\\GitHub\\LYAN_DSMS\\JavaApplication\\src\\main\\resources\\amogus.txt.sig.lyan");
//        SignedFile anotherSignedFile = SignedFile.readSignedFile(newFile);
//        System.out.println("Content: " + anotherSignedFile.getFileContent(null));
//    }

    public static void testUser() throws Exception {
        User myUser = User.createUser("Babao", "defiga");
        myUser.save("C:\\Users\\Win10\\Documents\\GitHub\\LYAN_DSMS\\JavaApplication\\src\\main\\resources\\");
        File userFile = new File("C:\\Users\\Win10\\Documents\\GitHub\\LYAN_DSMS\\JavaApplication\\src\\main\\resources\\Babao.user.lyan");
        User anotherUser = User.fromFile(userFile, "awkudhwak");
        System.out.println("Content: " + anotherUser.getUserName());
    }

    public static void main(String[] args) {
        String message = "a very secret message, do not let others than you read this";
        String key = "secret key";
        KeyPair keyPair = RSA.generateKeyPair();

        try {
//            testSignedFile(keyPair);
//            testUser();
            {
//                testAES(message, key);
                User filippo = User.createUser("Filippo", "raspberry");
                User paolo = User.createUser("Paolo", "sugoma");
                Certifier.initialize(RSA.generateKeyPair());
                SourceFile sourceFile = SourceFile.fromFile(new File("JavaApplication/res/test/amogus.txt"),
                        Arrays.asList(filippo.getPublicUser(), paolo.getPublicUser()));
                Certifier.Certificate certificate = Certifier.createCertificate(filippo.getPublicUser());
                certificate.save("JavaApplication/res/test/");
                SignedFile signedFile = new SignedFile(sourceFile, filippo, certificate);
                System.out.println("File signed by: " + signedFile.verifySignature());
                signedFile.save("JavaApplication/res/test/");
                signedFile = SignedFile.fromFile(
                        new File("JavaApplication/res/test/amogus.txt.sig.lyan"),
                        paolo);
                System.out.println("File signed by: " + signedFile.verifySignature());
//                signedFile.saveSourceFile("JavaApplication/res/test/");

            }
        }
        catch (Exception e) {
//            System.err.println(e.getMessage());
            e.printStackTrace();
        }

//        System.out.println("Original message: " + message);
//        testDigest(message);
//        testAES(message, key);
//        testRSA(message);
//        testCompression(message);
    }
}
