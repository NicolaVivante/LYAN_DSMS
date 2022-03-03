package it.castelli.lyan;

import it.castelli.encryption.RSA;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServerMiddlewareTest {

	@Test
	void userExists() {
		assertTrue(ServerMiddleware.userExists("luca"));
	}

	@Test
	void registerUser() {
		try {
			User user = User.createUser("test", "sas");
			ServerMiddleware.registerUser(user);
			assertEquals(user.getPublicUser(), ServerMiddleware.getPublicUser(user.getUserName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void getAllUsers() {

	}
}