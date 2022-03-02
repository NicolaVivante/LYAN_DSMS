package it.castelli.lyan;

import it.castelli.encryption.RSA;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServerMiddlewareTest {

    @Test
    void userExists() {
        // TODO: run
        assertTrue(ServerMiddleware.userExists("luca"));
    }

    @Test
    void registerUser() {
        try {
            // TODO: run
            ServerMiddleware.registerUser("test", "sus", "sas");
            assertEquals(new PublicUser("test", RSA.publicKeyFromString("sas")), ServerMiddleware.getUser("test"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getUser() {
        // TODO
    }

    @Test
    void getAllUsers() {
        // TODO
    }
}