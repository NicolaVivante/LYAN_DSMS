package it.castelli.lyan;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.castelli.encryption.AES;
import it.castelli.encryption.RSA;
import it.castelli.encryption.SHA_256;
import it.castelli.utils.Compressor;
import it.castelli.utils.Converter;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SignedFile {

    private final String fileName;
    private String fileContent;
    private boolean isEncrypted;
    private String signature;
    private Map<String, String> recipientsKeys;

    public static SignedFile readSignedFile(File signedFile) throws Exception {
        // from json

        return null;
    }

    public static SignedFile createSignedFile(File fileToSign, PrivateKey privateKey) throws Exception {
        return new SignedFile(fileToSign, privateKey);
    }

    public void toFile(String path) throws Exception {
        // to json and to file in file system
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonObject = objectMapper.writeValueAsString(this);
        String compressedJsonObject = Compressor.compress(jsonObject);

        String newFileName = path + this.fileName + ".sig.lyan";
        File newFile = new File(newFileName);
        if (newFile.createNewFile()) {
            FileWriter myWriter = new FileWriter(newFileName);
            myWriter.write(compressedJsonObject);
            myWriter.close();
        } else {
            throw new Exception("File already exists");
        }
    }

    private SignedFile(File file, PrivateKey privateKey) throws Exception {
        this.fileName = file.getName(); // gets file name
        byte[] fileContentBytes = Files.readAllBytes(file.toPath()); // read file content
        fileContent = Converter.byteArrayToString(fileContentBytes);
        String fileDigest = SHA_256.getDigest(fileContent); // create signature
        signature = RSA.encrypt(fileDigest, privateKey);
        isEncrypted = false; // not encrypted yet
    }

    /**
     * @param recipients a map containing pairs of userName and user public key
     */
    public void encrypt(Map<String, PublicKey> recipients) {
        isEncrypted = true;

        // encrypt file content and digest
        String encryptionKey = SHA_256.getDigest(fileContent);
        fileContent = AES.encrypt(fileContent, encryptionKey);
        signature = AES.encrypt(signature, encryptionKey);

        // add a key for each user given
        recipientsKeys = new HashMap<>();
        String userDigest, encryptedEncryptionKey;
        PublicKey publicKey;
        for (Map.Entry<String, PublicKey> recipient: recipients.entrySet()) {
            userDigest = SHA_256.getDigest(recipient.getKey());
            publicKey = recipient.getValue();
            encryptedEncryptionKey = RSA.encrypt(encryptionKey, publicKey);
            recipientsKeys.put(userDigest, encryptedEncryptionKey);
        }
    }
}
