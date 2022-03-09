package it.castelli.lyan;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.castelli.Extensions;
import it.castelli.Paths;
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

    private String password;
    private String userName;
    private String publicKeyString;
    private String privateKeyString;

    /**
     * Retrieve the user from a file and the corresponding password
     *
     * @param userFile The user file
     * @param password The password to unlock the user file
     * @return The user
     * @throws Exception An exception
     */
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

    /**
     * Generate a new User with given information
     *
     * @param userName The username
     * @param password The user password
     * @return A new user
     */
    public static User createUser(String userName, String password) {
        return new User(userName, password, RSA.generateKeyPair());
    }

    /**
     * Constructor for ObjectMapper
     */
    private User() {}

    /**
     * Internal constructor
     *
     * @param userName The username
     * @param password The user password
     * @param keyPair The user key pair
     */
    private User(String userName, String password, KeyPair keyPair) {
        this.userName = userName;
        this.password = password;
        privateKeyString = RSA.privateKeyToString(keyPair.getPrivate());
        publicKeyString = RSA.publicKeyToString(keyPair.getPublic());
    }

    /**
     * @return The username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return The user public key
     */
    @JsonIgnore
    public PublicKey getPublicKey() {
        return RSA.publicKeyFromString(publicKeyString);
    }

    /**
     * @return The user private key
     */
    @JsonIgnore
    public PrivateKey getPrivateKey() {
        return RSA.privateKeyFromString(privateKeyString);
    }

    /**
     * @return The public user generated from this user
     */
    @JsonIgnore
    public PublicUser getPublicUser() {
        return new PublicUser(userName, getPublicKey());
    }

    /**
     * Save the user into a file
     *
     * @throws Exception An exception
     */
    public void save() throws Exception {
        // to json and to file in file system
        String jsonObject = new ObjectMapper().writeValueAsString(this);
        String encryptedJsonObject = AES.encrypt(jsonObject, password);
        String compressedJsonObject = Compressor.compress(encryptedJsonObject);
        byte[] compressedJsonObjectBytes = Converter.stringToByteArray(compressedJsonObject);

        String newFileName = Paths.USERS_PATH + getUserName() + Extensions.USERS_EXTENSION;
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
