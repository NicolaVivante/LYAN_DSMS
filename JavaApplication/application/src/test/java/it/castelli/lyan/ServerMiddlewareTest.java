package it.castelli.lyan;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerMiddlewareTest {

	@Test
	void userExists() throws Exception{
		Assertions.assertTrue(ServerMiddleware.userExists("luca"));
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