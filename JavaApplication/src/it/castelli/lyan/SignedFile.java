package it.castelli.lyan;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.castelli.encryption.AES;
import it.castelli.encryption.RSA;
import it.castelli.encryption.SHA_256;
import it.castelli.utils.Compressor;
import it.castelli.utils.Converter;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SignedFile {

    @JsonIgnore
    private final static String EXTENSION = ".sig.lyan";

    private String fileName;
    private String fileContent;
    private boolean isEncrypted;
    private String signature;
    private Map<String, String> recipientsKeys;

    /**
     * Constructor for ObjectMapper
     */
    private SignedFile() {}

    /**
     * @param signedFile The signed file to parse
     * @return The parsed signed file
     * @throws Exception An exception
     */
    public static SignedFile readSignedFile(File signedFile) throws Exception {
        byte [] signedFileBytes = Files.readAllBytes(signedFile.toPath());
        String compressedJsonObject = Converter.byteArrayToString(signedFileBytes);
        String jsonObject = Compressor.decompress(compressedJsonObject);

        return new ObjectMapper().readValue(jsonObject, SignedFile.class);
    }

    /**
     * @param fileToSign The file to sign
     * @param privateKey The private key of the signer key pair
     * @return a new SignedFile
     * @throws Exception An exception
     */
    public static SignedFile createSignedFile(File fileToSign, PrivateKey privateKey) throws Exception {
        return new SignedFile(fileToSign, privateKey);
    }

    /**
     * @param path The path to save the file at (ending with \)
     * @throws Exception An exception
     */
    public void toFile(String path) throws Exception {
        // to json and to file in file system
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonObject = objectMapper.writeValueAsString(this);
        String compressedJsonObject = Compressor.compress(jsonObject);
        byte[] compressedJsonObjectBytes = Converter.stringToByteArray(compressedJsonObject);

        String newFileName = path + this.fileName + EXTENSION;
        File newFile = new File(newFileName);
        if (newFile.createNewFile()) {
            FileOutputStream outputStream = new FileOutputStream(newFileName);
            outputStream.write(compressedJsonObjectBytes);
            outputStream.close();
        } else {
            throw new Exception("File already exists");
        }
    }

    /**
     * @param fileToSign The file to sign
     * @param privateKey The private key of the signer key pair
     * @throws Exception An exception
     */
    private SignedFile(File fileToSign, PrivateKey privateKey) throws Exception {
        this.fileName = fileToSign.getName(); // gets file name
        byte[] fileContentBytes = Files.readAllBytes(fileToSign.toPath()); // read file content
        fileContent = Converter.byteArrayToString(fileContentBytes);
        String fileDigest = SHA_256.getDigest(fileContent); // create signature
        signature = RSA.encrypt(fileDigest, privateKey);
        isEncrypted = false; // not encrypted yet
    }

    /**
     * @param recipients The list of recipients
     */
    public void encrypt(List<PublicUser> recipients) {
        isEncrypted = true;

        // encrypt file content and digest
        String encryptionKey = SHA_256.getDigest(fileContent);
        fileContent = AES.encrypt(fileContent, encryptionKey);
        signature = AES.encrypt(signature, encryptionKey);

        // add a key for each user given
        recipientsKeys = new HashMap<>();
        String userDigest, encryptedEncryptionKey;
        for (PublicUser recipient : recipients) {
            userDigest = SHA_256.getDigest(recipient.getUserName());
            encryptedEncryptionKey = RSA.encrypt(encryptionKey, recipient.getPublicKey());
            recipientsKeys.put(userDigest, encryptedEncryptionKey);
        }
    }

    /**
     * @param currentUser The current user
     * @param signer The user who is supposed to have signed the file
     * @return whether the signer is the one who signed the file or not
     */
    public boolean verifySignature(User currentUser, PublicUser signer) throws Exception {
        String fileContentToVerify = fileContent;

        if(isEncrypted)
        {
            if(recipientsKeys.containsValue(SHA_256.getDigest(currentUser.getUserName())))
            {
                String symmetricKeyEncrypted = recipientsKeys.get(SHA_256.getDigest(currentUser.getUserName()));
                String symmetricKey = RSA.decrypt(symmetricKeyEncrypted, currentUser.getPrivateKey());
                fileContentToVerify = AES.decrypt(fileContent, symmetricKey);
            }
            else
                throw new Exception("Il file non può essere decifrato, perchè destinato ad altri!");
        }

        String fileContentToVerifyHash = SHA_256.getDigest(fileContentToVerify);
        String fileContentReceived = RSA.decrypt(signature, signer.getPublicKey());
        return fileContentToVerifyHash.equals(fileContentReceived);
    }

    //TODO : eliminate (temp method)
    public String getFileContent() {
        return fileContent;
    }
}
