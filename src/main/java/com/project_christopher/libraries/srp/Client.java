package com.project_christopher.libraries.srp;

import com.project_christopher.libraries.srp.Components.ClientState;
import com.project_christopher.libraries.srp.Components.M1AndA;
import com.project_christopher.libraries.srp.Components.Options;
import com.project_christopher.libraries.srp.Exceptions.BadServerCredentials;
import com.project_christopher.libraries.srp.Modules.Routines;

import java.math.BigInteger;

public class Client {

    private final Routines routines;

    private String I;
    private byte[] IH;
    private BigInteger A, a, M1, S;

    public Client(Options options) {
        this.routines = (options.routines != null ? options.routines : new Routines()).apply(options);

        if(options.clientState != null) {
            // filled after step1
            this.I = options.clientState.identity;
            this.IH = options.clientState.IH;

            // filled after step 2
            this.A = new BigInteger(options.clientState.A, 16);
            this.a = new BigInteger(options.clientState.a, 16);
            this.M1 = new BigInteger(options.clientState.M1, 16);
            this.S = new BigInteger(options.clientState.S, 16);
        }
    }

    /**
     * Stores the user's identity and generates IH (Identity Hash) using the user's password.
     * @param identity User identity.
     * @param password User password (not kept in state).
     */
    public void step1(String identity, String password) {
        if(identity == null || identity.trim().equals("")) throw new IllegalArgumentException("User identity (I) must not be null nor empty.");
        if(password == null || password.trim().equals("")) throw new IllegalArgumentException("User password (P) must not be null nor empty.");

        byte[] IH = this.routines.computeIdentityHash(identity, password);

        this.I = identity;
        this.IH = IH;
    }

    /**
     * Generates public and private values A and a.
     * Generates Client evidence message M1 and session key S.
     * @param salt Salt received from server.
     * @param B Server public key "B".
     */
    public M1AndA step2(String salt, String B) {
        if(salt == null || salt.trim().equals("")) throw new IllegalArgumentException("Salt (s) must not be null nor empty.");
        if(B == null || B.trim().equals("")) throw new IllegalArgumentException("Server's public value (B) must not be null nor empty.");

        BigInteger s = new BigInteger(salt, 16);
        BigInteger Bbi = new BigInteger(B, 16);

        BigInteger x = this.routines.computeXStep2(s, this.IH);
        BigInteger a = this.routines.generatePrivateValue();
        BigInteger A = this.routines.computeClientPublicValue(a);
        BigInteger k = this.routines.computeK();
        BigInteger u = this.routines.computeU(A, Bbi);
        BigInteger S = this.routines.computeClientSessionKey(k, x, u, a, Bbi);
        BigInteger M1 = this.routines.computeClientEvidence(this.I, s, A, Bbi, S);

        this.A = A;
        this.a = a;
        this.M1 = M1;
        this.S = S;

        return new M1AndA(A.toString(16), M1.toString(16));
    }

    /**
     * Checks if client and server is authenticated.
     * @param M2 Server message "M2".
     */
    public void step3(String M2) throws BadServerCredentials {
        if(M2 == null || M2.trim().equals("")) throw new IllegalArgumentException("Server evidence (M2) must not be null nor empty.");

        BigInteger M2bi = new BigInteger(M2, 16);

        BigInteger computedM2 = this.routines.computeServerEvidence(this.A, this.M1, this.S);
        if(!computedM2.equals(M2bi))
            throw new BadServerCredentials("Bad server credentials.");
    }

    /**
     * Exports identity, IH, A, a, M1 and S values.
     * Should be called after step1 or step2.
     */
    public ClientState toState() {
        return new ClientState(
                this.I,
                this.IH,
                this.A.toString(16),
                this.a.toString(16),
                this.M1.toString(16),
                this.S.toString(16)
        );
    }
}
