package com.project_christopher.libraries.srp.Modules;

import com.project_christopher.libraries.srp.Components.HashFunction;
import com.project_christopher.libraries.srp.Components.IVerifierAndSalt;
import com.project_christopher.libraries.srp.Components.Options;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Utils {

    /**
     * Left pad ArrayBuffer with zeroes.
     * @param bytes - ArrayBuffer to pad
     * @param targetLength Length of the target array in bytes.
     * @return Padded array or original array if targetLength is less than original
     *          array length.
     */
    public static byte[] padStartByteArray(byte[] bytes, int targetLength) {
        if (bytes.length < targetLength) {
            byte[] tmp = new byte[targetLength];
            Arrays.fill(tmp, (byte)0);
            System.arraycopy(bytes, 0, tmp, targetLength - bytes.length, bytes.length);
            return tmp;
        }

        return bytes;
    }

    /**
     * Generates a hash using an ArrayBuffer.
     * @param arrays The arrays that will be hashed.
     */
    public static byte[] hash(HashFunction hf, byte[] ...arrays) {
        int length = 0;
        for (byte[] array : arrays) length += array.length;

        byte[] target = new byte[length];
        for (int i = 0, offset = 0; i < arrays.length; i++) {
            System.arraycopy(arrays[i], 0, target, offset, arrays[i].length);
            offset += arrays[i].length;
        }

        return hf.call(target);
    }

    /**
     * Left pad in ArrayBuffer with zeroes and generates a hash from it.
     * @param targetLen Length of the target array in bytes.
     * @param arrays The arrays that the transformation will be applied.
     */
    public static byte[] hashPadded(HashFunction hf, int targetLen, byte[] ...arrays) {
        for (int i = 0; i < arrays.length; i++)
            arrays[i] = padStartByteArray(arrays[i], targetLen);

        return hash(hf, arrays);
    }

    /**
     * Generates random byte array.
     * @param numBytes Length of the byte array.
     */
    public static byte[] generateRandom(int numBytes) {
        return Routines.randomBytes.call(numBytes);
    }

    /**
     * Generates random string of ASCII characters using crypto secure random generator.
     * @param charCount The length of the result string.
     * @return string The random string.
     */
    public static String generateRandomString(int charCount) {
        byte[] bytes = generateRandom((int) Math.ceil(charCount / 2f));  // each byte has 2 hex digits
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Generates random big integer.
     * @param numBytes Length of the bigInt in bytes.
     */
    public static BigInteger generateRandomBigint(int numBytes) {
        return Transformations.byteArrayToBigint(generateRandom(numBytes));
    }

    /**
     * Generates a random verifier using the user's Identity, salt and Password.
     * @param I The user's identity.
     * @param s The random salt.
     * @param P The user's Password
     */
    public static BigInteger createVerifier(Options options, String I, BigInteger s, String P) {
        if(I == null || I.trim().equals("")) throw new IllegalArgumentException("Identity (I) must not be null or empty.");
        if(s == null) throw new IllegalArgumentException("Salt (s) must not be null.");
        if(P == null || P.trim().equals("")) throw new IllegalArgumentException("Password (P) must not be null or empty.");

        Routines routines = options.routines != null ? options.routines : new Routines();
        routines.apply(options);

        BigInteger x = routines.computeX(I, s, P);
        return routines.computeVerifier(x);
    }

    /**
     * Generates salt and verifier.
     * @param routines The routines used for hashing.
     * @param I The user's identity.
     * @param P The user's password.
     */
    public static IVerifierAndSalt generateVerifierAndSalt(Options options, String I, String P) {
        return generateVerifierAndSalt(options, I, P, null);
    }

    /**
     * Generates salt and verifier.
     * @param routines The routines used for hashing.
     * @param I The user's identity.
     * @param P The user's password.
     * @param sBytes Length of salt in bytes.
     */
    public static IVerifierAndSalt generateVerifierAndSalt(Options options, String I, String P, Integer sBytes) {
        options.routines = (options.routines != null ? options.routines : new Routines()).apply(options);

        BigInteger s = options.routines.generateRandomSalt(sBytes);

        return new IVerifierAndSalt(
                createVerifier(options, I, s, P).toString(16),
                s.toString(16)
        );
    }

    public static int hashBitCount(HashFunction hf) {
        return hash(hf, Transformations.bigintToByteArray(BigInteger.ONE)).length * 8;
    }
}
