package com.project_christopher.libraries.srp.Modules;

import java.math.BigInteger;

public class Routines {

    public Parameters parameters;

    public Routines() {
        this(new Parameters());
    }

    public Routines(Parameters parameters) {
        this.parameters = parameters;
    }

    /**
     * Generate a hash for multiple byte arrays.
     * @param arrays The byte arrays.
     */
    public byte[] hash(byte[] ...arrays) {
        return Utils.hash(this.parameters, arrays);
    }

    /**
     * Left pad in ArrayBuffer with zeroes and generates a hash from it.
     * @param arrays The ArrayBuffers.
     */
    public byte[] hashPadded(byte[] ...arrays) {
        int targetLength = (int) Math.floor((this.parameters.NBits + 7) / 8f);
        return Utils.hashPadded(this.parameters, targetLength, arrays);
    }

    /**
     * Computes K.
     */
    public BigInteger computeK() {
        return Transformations.byteArrayToBigint(
                this.hashPadded(
                        Transformations.bigintToByteArray(this.parameters.primeGroup.N),
                        Transformations.bigintToByteArray(this.parameters.primeGroup.g)
                )
        );
    }

    /**
     * Generates a random salt.
     * @param numBytes Length of salt in bytes.
     */
    public BigInteger generateRandomSalt(Integer numBytes) {
        int HBits = Utils.hashBitCount(this.parameters);

        int saltBytes = numBytes != null ? numBytes : (2 * HBits) / 8;
        return Utils.generateRandomBigint(saltBytes);
    }

    /**
     * Computes X.
     * @param I The user's identity.
     * @param s The random salt.
     * @param P The user's password.
     */
    public BigInteger computeX(String I, BigInteger s, String P) {
        return Transformations.byteArrayToBigint(
                this.hash(
                        Transformations.bigintToByteArray(s),
                        this.computeIdentityHash(I, P)
                )
        );
    }

    /**
     * Computes X for step 2.
     * @param s The user's salt
     * @param identityHash The generated identity hash.
     */
    public BigInteger computeXStep2(BigInteger s, byte[] identityHash) {
        return Transformations.byteArrayToBigint(
                this.hash(
                        Transformations.bigintToByteArray(s),
                        identityHash
                )
        );
    }

    /**
     * Generates an identity based on user's Identity and Password.
     * @param I The user's identity.
     * @param P The user's password.
     */
    public byte[] computeIdentityHash(String I, String P) {
        return this.hash(Transformations.stringToByteArray(I+":"+P));
    }

    /**
     * Generates a verifier based on x.
     * @param x The x.
     */
    public BigInteger computeVerifier(BigInteger x) {
        return this.parameters.primeGroup.g.modPow(x, this.parameters.primeGroup.N);
    }

    /**
     * Generates private value for server (b) or client (a).
     */
    public BigInteger generatePrivateValue() {
        int numBytes = Math.max(256, this.parameters.NBits);
        BigInteger bi;

        do {
            bi = Utils.generateRandomBigint(numBytes / 8).mod(this.parameters.primeGroup.N);
        }
        while (bi.equals(BigInteger.ZERO));

        return bi;
    }

    /**
     * Generates the public value for the client.
     * @param a The client's private value.
     */
    public BigInteger computeClientPublicValue(BigInteger a) {
        return this.parameters.primeGroup.g.modPow(a, this.parameters.primeGroup.N);
    }

    /**
     * Generates the public value for the client.
     * @param k The k.
     * @param v The verifier.
     * @param b The server's private value.
     */
    public BigInteger computeServerPublicValue(BigInteger k, BigInteger v, BigInteger b) {
        return this.parameters.primeGroup.g
                .modPow(b, this.parameters.primeGroup.N)
                .add(
                        v.multiply(k)
                )
                .mod(this.parameters.primeGroup.N);
    }

    /**
     * Checks if public value is valid.
     * @param value The value.
     */
    public boolean isValidPublicValue(BigInteger value) {
        return !value.mod(this.parameters.primeGroup.N).equals(BigInteger.ZERO);
    }

    /**
     * Computes U.
     * @param A The public value of client.
     * @param B The public value of server/\.
     */
    public BigInteger computeU(BigInteger A, BigInteger B) {
        return Transformations.byteArrayToBigint(
                this.hashPadded(
                        Transformations.bigintToByteArray(A),
                        Transformations.bigintToByteArray(B)
                )
        );
    }

    /**
     * Computes M1 which is the client's evidence.
     * @param I The user's identity.
     * @param s The random salt
     * @param A The client's public value.
     * @param B The server's public value.
     * @param S The session key.
     */
    public BigInteger computeClientEvidence(String I, BigInteger s, BigInteger A, BigInteger B, BigInteger S) {
        return Transformations.byteArrayToBigint(
                this.hash(
                        Transformations.stringToByteArray(I),
                        Transformations.bigintToByteArray(s),
                        Transformations.bigintToByteArray(A),
                        Transformations.bigintToByteArray(B),
                        Transformations.bigintToByteArray(S)
                )
        );
    }

    /**
     * Computes M2 which is the server's evidence.
     * @param A The client's public value.
     * @param M1 The client's evidence.
     * @param S The session key.
     */
    public BigInteger computeServerEvidence(BigInteger A, BigInteger M1, BigInteger S) {
        return Transformations.byteArrayToBigint(
                this.hash(
                        Transformations.bigintToByteArray(A),
                        Transformations.bigintToByteArray(M1),
                        Transformations.bigintToByteArray(S)
                )
        );
    }

    /**
     * Computes the session key S for the client.
     * @param k The k.
     * @param x The x.
     * @param u The u.
     * @param a The client's private value.
     * @param B The server's public value.
     */
    public BigInteger computeClientSessionKey(BigInteger k, BigInteger x, BigInteger u, BigInteger a, BigInteger B) {
        BigInteger N = this.parameters.primeGroup.N;
        BigInteger exp = u.multiply(x).add(a);
        BigInteger tmp = this.parameters.primeGroup.g.modPow(x, N).multiply(k).mod(N);

        return (B.add(N).subtract(tmp)).modPow(exp, N);
    }

    /**
     * Computes the session key S for the server.
     * @param v The verifier.
     * @param u The U.
     * @param A The client's public value.
     * @param b The server's private value.
     */
    public BigInteger computeServerSessionKey(BigInteger v, BigInteger u, BigInteger A, BigInteger b) {
        BigInteger N = this.parameters.primeGroup.N;
        return v.modPow(u, N).multiply(A).modPow(b, N);
    }
}
