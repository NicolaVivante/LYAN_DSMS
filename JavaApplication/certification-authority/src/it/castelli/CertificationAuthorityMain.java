package it.castelli;

import it.castelli.encryption.RSA;
import spark.Spark;

import java.security.KeyPair;

public class CertificationAuthorityMain {

    private static final String KEYS_PATH = "C:\\Users\\Win10\\Documents\\GitHub\\LYAN_DSMS\\JavaApplication\\certification-authority\\res\\";

    public static void main(String[] args) throws Exception {
        KeyPair keyPair = RSA.fromFile(KEYS_PATH);

        Spark.port(1111);

        Spark.post("encrypt", ((request, response) -> RSA.encrypt(request.body().toString(), keyPair.getPrivate())));

        Spark.post("decrypt", (request, response) -> RSA.decrypt(request.body().toString(), keyPair.getPublic()));
    }
}
