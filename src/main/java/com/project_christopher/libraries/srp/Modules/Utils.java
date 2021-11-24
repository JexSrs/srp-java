package com.project_christopher.libraries.srp.Modules;

import com.project_christopher.libraries.srp.Components.CompatibleCrypto;

import java.util.Arrays;

public class Utils {

    static {
        CompatibleCrypto cc = Crypto.compatibleCrypto();
    }

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
            // TODO - not sure
            System.arraycopy(bytes, 0, tmp, targetLength - bytes.length, bytes.length);
            return tmp;
        }

        return bytes;
    }

    /**
     * Generates a hash using an ArrayBuffer.
     * @param parameters The parameters used for hashing.
     * @param arrays The arrays that will be hashed.
     */
    public static byte[] hash(Parameters parameters, byte[] ...arrays) {
        
    }
}
