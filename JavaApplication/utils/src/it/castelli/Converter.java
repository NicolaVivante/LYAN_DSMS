package it.castelli;

import java.nio.charset.StandardCharsets;

public class Converter {

    public static String byteArrayToString(byte[] data) {
        return new String(data, StandardCharsets.ISO_8859_1);
    }

    public static byte[] stringToByteArray(String string) {
        return string.getBytes(StandardCharsets.ISO_8859_1);
    }
}
