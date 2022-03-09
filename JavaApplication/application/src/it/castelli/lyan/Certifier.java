package it.castelli.lyan;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.castelli.Extensions;
import it.castelli.Paths;
import it.castelli.encryption.RSA;
import it.castelli.Compressor;
import it.castelli.Converter;
import it.castelli.encryption.SHA_256;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.KeyPair;

public class Certifier {

    public static Certificate createCertificate(User user) throws Exception {
        String publicUserString = user.getPublicUser().toString();
        String signature = ServerMiddleware.encrypt(SHA_256.getDigest(publicUserString));
//        String signature = SignatureManager.getSignature(publicUserString, keyPair.getPrivate());
        return new Certificate(user.getPublicUser(), signature);
    }

    public static Certificate fromFile(File file) throws Exception {
        byte[] fileContentBytes = Files.readAllBytes(file.toPath());
        String compressedContent = Converter.byteArrayToString(fileContentBytes);
        String content = Compressor.decompress(compressedContent);
        return new ObjectMapper().readValue(content, Certificate.class);
    }

    public static boolean isValid(Certificate certificate) throws Exception {
        String content = certificate.getPublicUser().toString();
        String signature = certificate.getCertifierSignature();
        String calculatedDigest = SHA_256.getDigest(content);
        String certificateDigest = ServerMiddleware.decrypt(signature);
        return calculatedDigest.equals(certificateDigest);
//        return SignatureManager.verifySignature(content, signature, keyPair.getPublic());
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class Certificate {

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

        public void save() throws Exception {
            String certificateString = new ObjectMapper().writeValueAsString(this);
            String compressedString = Compressor.compress(certificateString);
            byte[] fileContentBytes = Converter.stringToByteArray(compressedString);
            String fileName = Paths.CERTIFICATES_PATH + this.publicUser.getUserName() + Extensions.CERTIFICATES_EXTENSION;
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
