package com.project_christopher.libraries.srp.Modules;

import com.project_christopher.libraries.srp.Components.CompatibleCrypto;
import com.project_christopher.libraries.srp.Components.HashFunction;
import com.project_christopher.libraries.srp.Components.RandomBytes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Crypto {

    /**
     * Returns the compatible crypto for this system.
     */
    static CompatibleCrypto compatibleCrypto() {
        Map<String, HashFunction> hashFunctions = new HashMap<>();
        RandomBytes randomBytes = array -> new int[0];

        hashFunctions.put("SHA1", data -> {
            try {
                return MessageDigest.getInstance("SHA-1").digest(data);
            }
            catch (NoSuchAlgorithmException ignored) {}
            return new byte[0];
        });
        hashFunctions.put("SHA256", data -> {
            try {
                return MessageDigest.getInstance("SHA-256").digest(data);
            }
            catch (NoSuchAlgorithmException ignored) {}
            return new byte[0];
        });
        hashFunctions.put("SHA384", data -> {
            try {
                return MessageDigest.getInstance("SHA-384").digest(data);
            }
            catch (NoSuchAlgorithmException ignored) {}
            return new byte[0];
        });
        hashFunctions.put("SHA512", data -> {
            try {
                return MessageDigest.getInstance("SHA-512").digest(data);
            }
            catch (NoSuchAlgorithmException ignored) {}
            return new byte[0];
        });

        return new CompatibleCrypto(hashFunctions, randomBytes);
    }
}
