package it.castelli.lyan;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.castelli.encryption.RSA;
import it.castelli.utils.Compressor;
import it.castelli.utils.Converter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyPair;

public class Certifier {

    private static KeyPair keyPair;
    private static boolean isInitialized = false;

    public static void initialize(KeyPair keys) {
        if (!isInitialized) {
            isInitialized = true;
            keyPair = keys;
        }
    }

    public static void saveKeys(String path) throws Exception {
        {
            String publicKeyString = RSA.publicKeyToString(keyPair.getPublic());
            byte[] fileContentBytes = Converter.stringToByteArray(publicKeyString);
            String fileName = path + "LYAN.key.public.txt";
            File newFile = new File(fileName);
            if (newFile.createNewFile()) {
                FileOutputStream outputStream = new FileOutputStream(fileName);
                outputStream.write(fileContentBytes);
                outputStream.close();
            } else {
                throw new Exception("File already exists");
            }
        }

        {
            String privateKeyString = RSA.privateKeyToString(keyPair.getPrivate());
            byte[] fileContentBytes = Converter.stringToByteArray(privateKeyString);
            String fileName = path + "LYAN.key.private.txt";
            File newFile = new File(fileName);
            if (newFile.createNewFile()) {
                FileOutputStream outputStream = new FileOutputStream(fileName);
                outputStream.write(fileContentBytes);
                outputStream.close();
            } else {
                throw new Exception("File already exists");
            }
        }
    }

    public static Certificate createCertificate(PublicUser publicUser) throws Exception {
        if (!isInitialized) throw new Exception("Certifier must be initialized");
        String publicUserString = publicUser.toString();
        String signature = SignatureManager.getSignature(publicUserString, keyPair.getPrivate());
        return new Certificate(publicUser, signature);
    }

    public static Certificate fromFile(File file) throws Exception {
        if (!isInitialized) throw new Exception("Certifier must be initialized");
        byte[] fileContentBytes = Files.readAllBytes(file.toPath());
        String compressedContent = Converter.byteArrayToString(fileContentBytes);
        String content = Compressor.decompress(compressedContent);
        return new ObjectMapper().readValue(content, Certificate.class);
    }

    public static boolean isValid(Certificate certificate) throws Exception {
        if (!isInitialized) throw new Exception("Certifier must be initialized");
        String content = certificate.getPublicUser().toString();
        String signature = certificate.getCertifierSignature();
        return SignatureManager.verifySignature(content, signature, keyPair.getPublic());
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class Certificate {

        @JsonIgnore
        private static final String EXTENSION = ".cert.lyan";

        private PublicUser publicUser;
        private String certifierSignature;

        private Certificate() {}

        private Certificate(PublicUser publicUser, String certifierSignature) {
            this.publicUser = publicUser;
            this.certifierSignature = certifierSignature;
        }

        public PublicUser getPublicUser() {
            return publicUser;
        }

        public String getCertifierSignature() {
            return certifierSignature;
        }

        public void save(String path) throws Exception {
            String certificateString = new ObjectMapper().writeValueAsString(this);
            String compressedString = Compressor.compress(certificateString);
            byte[] fileContentBytes = Converter.stringToByteArray(compressedString);
            String fileName = path + this.publicUser.getUserName() + EXTENSION;
            File newFile = new File(fileName);
            if (newFile.createNewFile()) {
                FileOutputStream outputStream = new FileOutputStream(fileName);
                outputStream.write(fileContentBytes);
                outputStream.close();
            } else {
                throw new Exception("File already exists");
            }
        }
    }
}
