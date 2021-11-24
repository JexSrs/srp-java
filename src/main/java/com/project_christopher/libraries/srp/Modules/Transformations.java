package com.project_christopher.libraries.srp.Modules;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class Transformations {

    /**
     * Convert a BigInteger into a byte array.
     * @param n Any big integer.
     */
    public static byte[] bigintToByteArray(BigInteger n) {
        return n.toByteArray();
    }

    /**
     * Convert a byte array into BigInteger.
     * @param bytes The byte array.
     */
    public static BigInteger byteArrayToBigint(byte[] bytes) {
        return new BigInteger(bytes);
    }

    /**
     * Convert string into ArrayBuffer.
     * @param s Any UTF8 string.
     */
    public static byte[] stringToByteArray(String s) {
        return s.getBytes(StandardCharsets.UTF_8);
    }
}
