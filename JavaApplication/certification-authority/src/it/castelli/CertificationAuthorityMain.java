package it.castelli;

import it.castelli.encryption.RSA;
import spark.Spark;

import java.security.KeyPair;

public class CertificationAuthorityMain {
    public static void main(String[] args) throws Exception {
        KeyPair keyPair = RSA.fromFile("C:\\Users\\Win10\\Documents\\GitHub\\LYAN_DSMS\\JavaApplication\\certification-authority\\res\\");

        Spark.port(1111);

        Spark.post("encrypt", ((request, response) -> RSA.encrypt(request.body().toString(), keyPair.getPrivate())));

        Spark.post("decrypt", (request, response) -> RSA.decrypt(request.body().toString(), keyPair.getPublic()));
    }
}
