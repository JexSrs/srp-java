package com.project_christopher.libraries.srp.Modules;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Transformations {

    /**
     * Convert a BigInteger into a byte array.
     * @param n Any big integer.
     */
    public static byte[] bigintToByteArray(BigInteger n) {
        byte[] bytes = n.toByteArray();
        if (bytes[0] == 0)
            return Arrays.copyOfRange(bytes, 1, bytes.length);

        return bytes;
    }

    /**
     * Convert a byte array into BigInteger.
     * @param bytes The byte array.
     */
    public static BigInteger byteArrayToBigint(byte[] bytes) {
        return new BigInteger(1, bytes);
    }

    /**
     * Convert string into ArrayBuffer.
     * @param s Any UTF8 string.
     */
    public static byte[] stringToByteArray(String s) {
        return s.getBytes(StandardCharsets.UTF_8);
    }
}
