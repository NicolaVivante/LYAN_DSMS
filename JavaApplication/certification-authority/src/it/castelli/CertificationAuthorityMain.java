package it.castelli;

import it.castelli.encryption.RSA;
import spark.Spark;

import java.io.File;
import java.security.KeyPair;

public class CertificationAuthorityMain {

    private static final String NAME = "certificationAuthority";

    public static void main(String[] args) throws Exception {
        File publicKeyFile, privateKeyFile;
        publicKeyFile = new File(Paths.KEYS_PATH + NAME + Extensions.PUBLIC_KEYS_EXTENSION);
        privateKeyFile = new File(Paths.KEYS_PATH + NAME + Extensions.PRIVATE_KEYS_EXTENSION);
        KeyPair keyPair = RSA.fromFile(publicKeyFile, privateKeyFile);

        Spark.port(11111);

        Spark.post("encrypt", ((request, response) -> RSA.encrypt(request.body().toString(), keyPair.getPrivate())));

        Spark.post("decrypt", (request, response) -> RSA.decrypt(request.body().toString(), keyPair.getPublic()));
    }
}
