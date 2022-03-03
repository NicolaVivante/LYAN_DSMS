package it.castelli.utils;

import java.nio.charset.StandardCharsets;

public class Converter {

    public static String byteArrayToString(byte[] data) {
        return new String(data, StandardCharsets.ISO_8859_1);
    }

    public static byte[] stringToByteArray(String string) {
        return string.getBytes(StandardCharsets.ISO_8859_1);
    }

    public static String ISO_8859_1ToUTF_8(String ISO_8859_1String) {
        byte[] bytes = stringToByteArray(ISO_8859_1String);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static String UTF_8ToISO_8859_1(String UTF_8String) {
        byte[] bytes = UTF_8String.getBytes(StandardCharsets.UTF_8);
        return byteArrayToString(bytes);
    }
}
