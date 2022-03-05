package it.castelli;

import it.castelli.encryption.RSA;
import it.castelli.lyan.Certifier;
import it.castelli.lyan.SignedFile;
import it.castelli.lyan.SourceFile;
import it.castelli.lyan.User;

import java.io.File;
import java.security.KeyPair;
import java.util.Arrays;

public class ApplicationMain {

	public static void main(String[] args) {
		String message = "a very secret message, do not let others than you read this";
		String key = "secret key";
		KeyPair keyPair = RSA.generateKeyPair();

		try {
			{
//                testAES(message, key);
				User filippo = User.createUser("Filippo", "raspberry");
//                User filippo = User.fromFile(new File
//                ("C:\\Users\\Win10\\Documents\\GitHub\\LYAN_DSMS\\JavaApplication\\src\\main\\resources\\test
//                \\Filippo.user.lyan"), "raspberry");
				filippo.save("C:\\Users\\Win10\\Documents\\GitHub\\LYAN_DSMS\\JavaApplication\\src\\main\\resources" +
                        "\\test\\");
				User paolo = User.createUser("Paolo", "sugoma");
				Certifier.initialize(RSA.generateKeyPair());
				Certifier.saveKeys("C:\\Users\\Win10\\Documents\\GitHub\\LYAN_DSMS\\JavaApplication\\src\\main" +
                        "\\resources\\test\\");
				SourceFile sourceFile =
						SourceFile.fromFile(new File("C:\\Users\\Win10\\Documents\\GitHub\\LYAN_DSMS\\JavaApplication" +
                                        "\\src\\main\\resources\\amogus.txt"),
								Arrays.asList(filippo.getPublicUser(), paolo.getPublicUser()));
				Certifier.Certificate certificate = Certifier.createCertificate(filippo);
//                Certifier.Certificate certificate = Certifier.fromFile(new File
//                ("C:\\Users\\Win10\\Documents\\GitHub\\LYAN_DSMS\\JavaApplication\\src\\main\\resources\\test
//                \\Filippo.cert.lyan"));
				certificate.save("C:\\Users\\Win10\\Documents\\GitHub\\LYAN_DSMS\\JavaApplication\\src\\main" +
                        "\\resources\\test\\");
				SignedFile signedFile = new SignedFile(sourceFile, filippo, certificate);
				System.out.println("File signed by: " + signedFile.verifySignature());
				signedFile.save("C:\\Users\\Win10\\Documents\\GitHub\\LYAN_DSMS\\JavaApplication\\src\\main\\resources" +
                        "\\test\\");
				signedFile = SignedFile.fromFile(
						new File("C:\\Users\\Win10\\Documents\\GitHub\\LYAN_DSMS\\JavaApplication\\src\\main" +
                                "\\resources\\test\\amogus.txt.sig.lyan"),
						paolo);
				System.out.println("File signed by: " + signedFile.verifySignature());
				signedFile.saveSourceFile("C:\\Users\\Win10\\Documents\\GitHub\\LYAN_DSMS\\JavaApplication\\src\\main" +
                        "\\resources\\test\\");
			}
		} catch (Exception e) {
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
