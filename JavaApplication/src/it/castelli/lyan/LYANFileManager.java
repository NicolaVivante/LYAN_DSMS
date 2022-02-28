package it.castelli.lyan;

import java.io.File;
import java.io.FileWriter;
import java.security.PublicKey;

public class LYANFileManager {

    private final String FILE_NAME = "fileName";
    private final String FILE_CONTENT = "fileContent";
    private final String IS_ENCRYPTED = "isEncrypted";
    private final String SIGNATURE = "signature";
    private final String KEY = "key";

    public File getPublicKeyFile(PublicKey key, String userName) {
        String fileName = userName + ".key_public.lyan";
        File publicKeyFile = new File(fileName);
        try {
            if (publicKeyFile.createNewFile()) {
                FileWriter myWriter = new FileWriter(fileName);



                myWriter.write("content");
                myWriter.close();
            } else {
                System.out.println("File already exists.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return publicKeyFile;
    }

}
