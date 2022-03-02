package it.castelli.lyan;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.castelli.utils.Converter;

import java.io.File;
import java.io.FileOutputStream;
import java.security.KeyPair;

public class Certifier {

    private static KeyPair keyPair;
    private static boolean isInitialized = false;

    public static void initialize(KeyPair keys) {
        isInitialized = true;
        keyPair = keys;
    }

    public static Certificate getCertificate(PublicUser publicUser) throws Exception {
        if (!isInitialized) throw new Exception("Certifier must be initialized");
        String publicUserString = publicUser.toString();
        String signature = SignatureManager.getSignature(publicUserString, keyPair.getPrivate());
        return new Certificate(publicUser, signature);
    }

    public static boolean verifyCertificate(Certificate certificate) throws Exception {
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
            byte[] fileContentBytes = Converter.stringToByteArray(certificateString);
            String fileName = path + this.publicUser.getUserName();
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
