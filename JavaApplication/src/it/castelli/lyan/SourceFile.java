package it.castelli.lyan;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import it.castelli.encryption.AES;
import it.castelli.encryption.RSA;
import it.castelli.encryption.SHA_256;
import it.castelli.utils.Converter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SourceFile {
    private String fileName;
    private String fileContent;
    private boolean isEncrypted;
    private Map<String, String> recipientsKeys;

    private SourceFile() {}

    public SourceFile(File file) {
        try {
            fileName = file.getName();
            byte[] fileContentBytes = Files.readAllBytes(file.toPath());
            fileContent = Converter.byteArrayToString(fileContentBytes);
            isEncrypted = false;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SourceFile(String fileName, String fileContent) {
        this.fileName = fileName;
        this.fileContent = fileContent;
        isEncrypted = false;
    }

    public SourceFile(File file, List<PublicUser> recipients) {
        this(file);
        encrypt(recipients);
    }

    private void encrypt(List<PublicUser> recipients) {
        isEncrypted = true;

        // get encryption key
        String key = SHA_256.getDigest(fileContent);

        // encrypt file name and content
        fileName = AES.encrypt(fileName, key);
        fileContent = AES.encrypt(fileContent, key);

        // add a key for each given recipient
        recipientsKeys = new HashMap<>();
        for (PublicUser recipient: recipients) {
            String userDigest = SHA_256.getDigest(recipient.getUserName());
            String encryptedKey = RSA.encrypt(key, recipient.getPublicKey());
            recipientsKeys.put(userDigest, encryptedKey);
        }
    }

    private SourceFile decrypt(User user) throws Exception {
        String userNameDigest = SHA_256.getDigest(user.getUserName());
        if (recipientsKeys.containsKey(userNameDigest)) {
            // get decryption key
            String encryptedKey = recipientsKeys.get(userNameDigest);
            String key = RSA.decrypt(encryptedKey, user.getPrivateKey());

            // decrypt file name and content
            String fileName = AES.decrypt(this.fileName, key);
            String fileContent = AES.decrypt(this.fileContent, key);
            return new SourceFile(fileName, fileContent);

        } else {
            throw new Exception("You are not allowed to read this file");
        }
    }

    public SourceFile getAccess(User user) throws Exception {
        if (isEncrypted) {
            return decrypt(user);
        } else {
            return this;
        }
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileContent() {
        return fileContent;
    }
}
