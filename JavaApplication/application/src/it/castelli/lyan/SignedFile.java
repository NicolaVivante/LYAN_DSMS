package it.castelli.lyan;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.castelli.Compressor;
import it.castelli.Converter;
import it.castelli.Extensions;
import it.castelli.Paths;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SignedFile {

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

    public void save() throws Exception {
        // to json and to file in file system
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonObject = objectMapper.writeValueAsString(this);
        String compressedJsonObject = Compressor.compress(jsonObject);
        byte[] compressedJsonObjectBytes = Converter.stringToByteArray(compressedJsonObject);

        String newFileName = Paths.SIGNED_FILES_PATH + sourceFileUnlocked.getFileName() + Extensions.SIGNED_FILES_EXTENSION;
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

    public String getSignatory() throws Exception {
        if (isValid()) {
            return certificate.getPublicUser().getUserName();
        } else {
            throw new Exception("Invalid signature!");
        }
    }

    public boolean isValid() throws Exception {
        String fileContent = sourceFileUnlocked.getFileContent();
        return SignatureManager.verifySignature(fileContent, signature, certificate);
    }

    public void saveSourceFile() throws Exception {
        sourceFileUnlocked.save();
    }
}
