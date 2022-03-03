package it.castelli.lyan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.castelli.encryption.RSA;
import it.castelli.encryption.SHA_256;
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
	 * @param user The user to add
	 */
	public static void registerUser(User user) throws Exception {
		String body = "{ \"userName\": \"" +
				user.getUserName() + "\", \"passwordHash\": \"" +
				SHA_256.getDigest(user.getPassword()) + "\", \"publicKey\": " +
				RSA.publicKeyToString(user.getPublicKey()) + "\" }";
		System.out.println(body);
		int status = Unirest.post(serverAddress + "users").body(body).asJson().getStatus();
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
			String responseBody =
					Unirest.get(serverAddress + "users").queryString("userName", userName).asString().getBody();
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
