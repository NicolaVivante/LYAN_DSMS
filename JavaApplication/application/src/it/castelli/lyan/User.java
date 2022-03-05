package it.castelli.lyan;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.castelli.encryption.AES;
import it.castelli.encryption.RSA;
import it.castelli.Compressor;
import it.castelli.Converter;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class User {

    @JsonIgnore
    private final static String EXTENSION = ".user.lyan";

    private String password;
    private String userName;
    private String publicKeyString;
    private String privateKeyString;

    public static User fromFile(File userFile, String password) throws Exception {
        byte [] userBytes = Files.readAllBytes(userFile.toPath());
        String compressedJsonObject = Converter.byteArrayToString(userBytes);
        String encryptedJsonObject = Compressor.decompress(compressedJsonObject);
        String jsonObject = AES.decrypt(encryptedJsonObject, password);
        if (jsonObject.equals("")) {
            throw new Exception("Wrong password");
        } else {
            return new ObjectMapper().readValue(jsonObject, User.class);
        }
    }

    public static User createUser(String userName, String password) {
        KeyPair newKeyPair = RSA.generateKeyPair();
        return new User(userName, password, newKeyPair);
    }

    /**
     * Constructor for ObjectMapper
     */
    private User() {}

    private User(String userName, String password, KeyPair keyPair) {
        this.userName = userName;
        this.password = password;
        privateKeyString = RSA.privateKeyToString(keyPair.getPrivate());
        publicKeyString = RSA.publicKeyToString(keyPair.getPublic());
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @JsonIgnore
    public PublicKey getPublicKey() {
        return RSA.publicKeyFromString(publicKeyString);
    }

    @JsonIgnore
    public PrivateKey getPrivateKey() {
        return RSA.privateKeyFromString(privateKeyString);
    }

    @JsonIgnore
    public PublicUser getPublicUser() {
        return new PublicUser(userName, getPublicKey());
    }

    public void save(String path) throws Exception {
        // to json and to file in file system
        String jsonObject = new ObjectMapper().writeValueAsString(this);
        String encryptedJsonObject = AES.encrypt(jsonObject, password);
        String compressedJsonObject = Compressor.compress(encryptedJsonObject);
        byte[] compressedJsonObjectBytes = Converter.stringToByteArray(compressedJsonObject);

        String newFileName = path + getUserName() + EXTENSION;
        File newFile = new File(newFileName);
        if (newFile.createNewFile()) {
            FileOutputStream outputStream = new FileOutputStream(newFileName);
            outputStream.write(compressedJsonObjectBytes);
            outputStream.close();
        } else {
            throw new Exception("File already exists");
        }
    }
}
