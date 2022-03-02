package it.castelli.lyan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.Unirest;

public class ServerMiddleware {
    public static final String serverAddress = "http://localhost:5555/";

    /**
     * Is the user with the specified userName registered in the db?
     *
     * @param userName The name of the user
     * @return true if the user exists, false if not
     */
    public static boolean userExists(String userName) {
        return getUser(userName) == null;
    }

    /**
     * Add a new user in the db
     *
     * @param userName     The name of the user
     * @param passwordHash The hash of the password of the user
     * @param publicKey    The public key of the certificate of the user
     */
    public static void registerUser(String userName, String passwordHash, String publicKey) throws Exception {
        String body = "{ \"userName\": \"" +
                userName + "\", \"passwordHash\": \"" +
                passwordHash + "\", \"publicKey\": " +
                publicKey + "\", \"key\": \"sus\" }";
        int status = Unirest.post(serverAddress + "users").body(body).asEmpty().getStatus();
        if (status >= 500) throw new Exception("Server error while adding the user");
    }

    /**
     * Get a specific user from the db
     *
     * @param userName The name of the user (unique)
     * @return The PublicUser, null if there are errors
     */
    public static PublicUser getUser(String userName) {
        try {
            String responseBody = Unirest.get(serverAddress + "users").queryString("userName", userName).asString().getBody();
            return new ObjectMapper().readValue(responseBody, PublicUser.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get all the users in the db
     *
     * @return An array of users, empty if there are errors
     */
    public static PublicUser[] getAllUsers() {
        try {
            String responseBody = Unirest.get(serverAddress + "users").asString().getBody();
            return new ObjectMapper().readValue(responseBody, PublicUser[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new PublicUser[0];
        }
    }
}
