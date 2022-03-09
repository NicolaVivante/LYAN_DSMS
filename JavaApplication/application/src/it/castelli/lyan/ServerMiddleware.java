package it.castelli.lyan;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.castelli.encryption.RSA;
import kong.unirest.Unirest;

import java.net.ConnectException;

public class ServerMiddleware {
    public static final String DB_SERVER_ADDRESS = "http://localhost:5555/";
    public static final String CERTIFICATION_AUTHORITY_SERVER_ADDRESS = "http://localhost:11111/";

    /**
     * Is the user with the specified userName registered in the db?
     *
     * @param userName The name of the user
     * @return true if the user exists, false if not
     */
    public static boolean userExists(String userName) throws Exception{
        return getPublicUser(userName) != null;
    }

    /**
     * Add a new user in the db
     *
     * @param user The user to add
     */
    public static void registerUser(User user) throws Exception {
        record UserJson(String userName, String publicKeyString) {}
        String body = new ObjectMapper().writeValueAsString(
                new UserJson(user.getUserName(), RSA.publicKeyToString(user.getPublicKey())));
        try {
            int status = Unirest.post(DB_SERVER_ADDRESS + "users")
                    .header("Content-Type", "application/json")
                    .header("accept", "application/json")
                    .body(body).asJson().getStatus();
            if (status >= 500) {
                throw new Exception("Server error while adding the user");
            }
        } catch (RuntimeException e) {
            throw new Exception("Cannot connect with users DB");
        }
    }

    /**
     * Get a specific user from the db
     *
     * @param userName The name of the user (unique)
     * @return The PublicUser, null if there are errors
     */
    public static PublicUser getPublicUser(String userName) throws Exception{
        try {
            String responseBody =
                    Unirest.get(DB_SERVER_ADDRESS + "users").queryString("userName", userName).asString().getBody();
            return new ObjectMapper().readValue(responseBody, PublicUser.class);
        }
        catch (RuntimeException e) {
            throw new Exception("Cannot connect with users DB");
        }
        catch (Exception e) {
//            e.printStackTrace();
            return null;
        }

    }

    /**
     * Get all the users in the db
     *
     * @return An array of users, empty if there are errors
     */
    public static PublicUser[] getAllUsers() throws Exception {
        try {
            String responseBody = Unirest.get(DB_SERVER_ADDRESS + "users").asString().getBody();
            return new ObjectMapper().readValue(responseBody, PublicUser[].class);
        }
        catch (RuntimeException e) {
            throw new Exception("Cannot connect with users DB");
        }
        catch (Exception e) {
//            e.printStackTrace();
            return new PublicUser[0];
        }
    }

    /**
     * Encrypt the given content with certification authority public key
     *
     * @param content The content to encrypt
     * @return The encrypted content
     * @throws Exception An exception
     */
    public static String encrypt(String content) throws Exception {
        try {
            return Unirest.post(CERTIFICATION_AUTHORITY_SERVER_ADDRESS + "encrypt").body(content).asString().getBody();
        }
        catch (RuntimeException e) {
            throw new Exception("Cannot connect with certification authority");
        }
    }

    /**
     * Decrypt the given content with certification authority private key
     *
     * @param content The content to decrypt
     * @return The decrypted content
     * @throws Exception An exception
     */
    public static String decrypt(String content) throws Exception{
        try {
            return Unirest.post(CERTIFICATION_AUTHORITY_SERVER_ADDRESS + "decrypt").body(content).asString().getBody();
        }
        catch (RuntimeException e) {
            throw new Exception("Cannot connect with certification authority");
        }
    }
}
