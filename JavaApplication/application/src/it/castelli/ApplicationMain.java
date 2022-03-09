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
        try {
                User pouy = User.fromFile(new File("C:\\Users\\Win10\\Documents\\GitHub\\LYAN_DSMS\\JavaApplication\\src\\main\\resources\\test\\POUY.user.lyan"), "HI");

                SourceFile sourceFile = SourceFile.fromFile(new File("C:\\Users\\Win10\\Documents\\GitHub\\LYAN_DSMS\\JavaApplication\\src\\main\\resources\\test\\amogus.txt"));
                Certifier.Certificate certificate = Certifier.createCertificate(pouy);

                SignedFile signedFile = new SignedFile(sourceFile, pouy, certificate);
                signedFile.save();
        }
        catch (Exception e) {
//            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
