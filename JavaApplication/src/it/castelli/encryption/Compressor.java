package it.castelli.encryption;

import java.io.ByteArrayOutputStream;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

public class Compressor {

    private static byte[] compress(byte[] data) throws Exception {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(arrayOutputStream);
        for (byte b: data) {
            deflaterOutputStream.write(b);
        }
        deflaterOutputStream.close();
        return arrayOutputStream.toByteArray();
    }

    private static byte[] decompress(byte[] data) throws Exception {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        InflaterOutputStream outputStream = new InflaterOutputStream(arrayOutputStream);
        for (byte b: data) {
            outputStream.write(b);
        }
        outputStream.close();
        return arrayOutputStream.toByteArray();
    }

    public static String compress(String s) {

        try {
            byte[] bytes = Converter.stringToByteArray(s);
            byte[] compressedBytes = compress(bytes);
            return Converter.byteArrayToString(compressedBytes);
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String decompress(String s) {
        try {
            byte[] compressedBytes = Converter.stringToByteArray(s);
            byte[] decompressedBytes = decompress(compressedBytes);
            return Converter.byteArrayToString(decompressedBytes);
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}