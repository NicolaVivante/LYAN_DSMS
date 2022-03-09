package it.castelli.lyan;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class SignedFileTest {

	@Test
	void fromFile() throws Exception {
		User luca = User.fromFile(new File("C:\\Users\\asus\\Downloads\\Luca.user.lyan"), "password");
		User pluto = User.fromFile(new File("C:\\Users\\asus\\Downloads\\Pluto.user.lyan"), "drowssap");
		ArrayList<PublicUser> users = new ArrayList<>(List.of(ServerMiddleware.getAllUsers()));
		users.remove(pluto.getPublicUser());
		SourceFile sourceFile = SourceFile.createSourceFile(new File("C:\\Users\\asus\\Desktop\\diarioDiStage.txt"), users);
		SignedFile signedFile = new SignedFile(sourceFile, luca, Certifier.createCertificate(luca));
		signedFile.save();
	}

	@Test
	void save() {
	}

	@Test
	void verifySignature() throws Exception {
		User user = User.fromFile(new File("C:\\Users\\asus\\Downloads\\Pippo.user.lyan"), "password");
		SignedFile signedFile = SignedFile.fromFile(new File("C:\\Users\\asus\\Desktop\\diarioDiStage.txt.sig.lyan"),
				user);
		Assertions.assertEquals("Luca", signedFile.getSignatory());
	}

	@Test
	void saveSourceFile() {
	}
}