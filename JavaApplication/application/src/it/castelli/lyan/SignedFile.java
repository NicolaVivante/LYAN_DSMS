package it.castelli.lyan;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.castelli.Compressor;
import it.castelli.Converter;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.PublicKey;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SignedFile {

    @JsonIgnore
    private final static String EXTENSION = ".sig.lyan";

    private Certifier.Certificate certificate;
    private SourceFile sourceFile;
    private String signature;

    @JsonIgnore
    private SourceFile sourceFileUnlocked;

    private SignedFile() {}

    public static SignedFile fromFile(File file, User user) throws Exception {
        byte[] signedFileBytes = Files.readAllBytes(file.toPath());
        String compressedJsonObject = Converter.byteArrayToString(signedFileBytes);
        String jsonObject = Compressor.decompress(compressedJsonObject);

        SignedFile signedFile = new ObjectMapper().readValue(jsonObject, SignedFile.class);
        signedFile.sourceFileUnlocked = signedFile.sourceFile.decrypt(user);
        return signedFile;
    }

    public void save(String path) throws Exception {
        // to json and to file in file system
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonObject = objectMapper.writeValueAsString(this);
        String compressedJsonObject = Compressor.compress(jsonObject);
        byte[] compressedJsonObjectBytes = Converter.stringToByteArray(compressedJsonObject);

        String newFileName = path + sourceFileUnlocked.getFileName() + EXTENSION;
        File newFile = new File(newFileName);
        if (newFile.createNewFile()) {
            FileOutputStream outputStream = new FileOutputStream(newFileName);
            outputStream.write(compressedJsonObjectBytes);
            outputStream.close();
        } else {
            throw new Exception("File already exists");
        }
    }

    public SignedFile(SourceFile sourceFile, User user, Certifier.Certificate certificate) throws Exception {
        this.sourceFile = sourceFile;
        this.sourceFileUnlocked = sourceFile.decrypt(user);
        String fileContent = sourceFileUnlocked.getFileContent();
        if (!Certifier.isValid(certificate)) throw new Exception("Invalid certificate!");
        if (!certificate.getPublicUser().equals(user.getPublicUser())) throw new Exception("Certificate user and given user don't correspond!");
        this.signature = SignatureManager.getSignature(fileContent, user.getPrivateKey());
        this.certificate = certificate;
    }

    public String verifySignature() throws Exception {
        String fileContent = sourceFileUnlocked.getFileContent();
        if (!Certifier.isValid(certificate)) throw new Exception("Invalid certificate!");
        PublicKey publicKey = certificate.getPublicUser().getPublicKey();
        if (SignatureManager.verifySignature(fileContent, signature, publicKey)) {
            return certificate.getPublicUser().getUserName();
        } else {
            throw new Exception("Invalid signature!");
        }
    }

    public void saveSourceFile(String path) throws Exception {
        sourceFileUnlocked.save(path);
    }
}
