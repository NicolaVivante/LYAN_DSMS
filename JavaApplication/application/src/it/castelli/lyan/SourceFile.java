package it.castelli.lyan;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import it.castelli.Paths;
import it.castelli.encryption.AES;
import it.castelli.encryption.RSA;
import it.castelli.encryption.SHA_256;
import it.castelli.Converter;

import java.io.File;
import java.io.FileOutputStream;
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

    /**
     * * Create a new SourceFile from a file
     *
     * @param file The file to wrap
     * @return The SourceFile containing the original one, not encrypted
     * @throws Exception An exception
     */
    public static SourceFile createSourceFile(File file) throws Exception {
        String fileName = file.getName();
        byte[] fileContentBytes = Files.readAllBytes(file.toPath());
        String fileContent = Converter.byteArrayToString(fileContentBytes);
        return new SourceFile(fileName, fileContent);
    }

    /**
     * Create a new SourceFile from a file and a list of recipients and encrypt it
     *
     * @param file       The file
     * @param recipients The list of public users who are able to access the file once encrypted
     * @return The SourceFile containing the original one, encrypted, only recipients can access it
     * @throws Exception An exception
     */
    public static SourceFile createSourceFile(File file, List<PublicUser> recipients) throws Exception {
        String fileName = file.getName();
        byte[] fileContentBytes = Files.readAllBytes(file.toPath());
        String fileContent = Converter.byteArrayToString(fileContentBytes);
        SourceFile sourceFile = new SourceFile(fileName, fileContent);
        sourceFile.encrypt(recipients);
        return sourceFile;
    }

    /**
     * Decrypt the contained file
     *
     * @param user The user that tries to access the file
     * @return A decrypted version of the SourceFile itself
     * @throws Exception An exception
     */
    public static SourceFile decrypt(SourceFile sourceFile, User user) throws Exception {
        if (!sourceFile.isEncrypted) return sourceFile;

        String userNameDigest = SHA_256.getDigest(user.getUserName());
        if (sourceFile.recipientsKeys.containsKey(userNameDigest)) {
            // get decryption key
            String encryptedKey = sourceFile.recipientsKeys.get(userNameDigest);
            String key = RSA.decrypt(encryptedKey, user.getPrivateKey());

            // decrypt file name and content
            String fileName = AES.decrypt(sourceFile.fileName, key);
            String fileContent = AES.decrypt(sourceFile.fileContent, key);
            return new SourceFile(fileName, fileContent);

        } else {
            throw new Exception("You are not allowed to read this file");
        }
    }

    /**
     * Internal constructor
     *
     * @param fileName    The contained file name
     * @param fileContent The contained file content
     */
    private SourceFile(String fileName, String fileContent) {
        this.fileName = fileName;
        this.fileContent = fileContent;
        isEncrypted = false;
    }

    /**
     * Constructor for ObjectMapper
     */
    private SourceFile() {
    }

    /**
     * Encrypt the contained file
     *
     * @param recipients The list of public users who are able to access the file once encrypted
     * @throws Exception An exception
     */
    private void encrypt(List<PublicUser> recipients) throws Exception {
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

    /**
     * @return The contained file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return The contained file content
     */
    public String getFileContent() {
        return fileContent;
    }

    /**
     * Save the contained file
     *
     * @throws Exception An exception
     */
    public void saveFile() throws Exception {
        byte[] fileContentBytes = Converter.stringToByteArray(fileContent);
        String fileName = Paths.EXTRACTED_FILES_PATH + this.fileName;
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
