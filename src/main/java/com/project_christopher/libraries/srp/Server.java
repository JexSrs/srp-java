package com.project_christopher.libraries.srp;

import com.project_christopher.libraries.srp.Components.ServerState;
import com.project_christopher.libraries.srp.Modules.Routines;

import java.math.BigInteger;
import java.util.Objects;

public class Server {

    private Routines routines;
    private String I;
    private BigInteger salt, verifier, B, b;

    public Server(Routines routines) {
        this.routines = routines;
    }


    /**
     * Stores identity, salt and verifier.
     * Generates public and private keys B and b.
     * @param identity User's identity.
     * @param salt Salt stored in database.
     * @param verifier Verifier stored in database.
     * @return B Server's public key.
     */
    public String step1(String identity, String salt, String verifier) {
        if(identity == null || identity.trim().equals("")) throw new NullPointerException("Identity must not be null nor empty.");
        if(salt == null || salt.trim().equals("")) throw new NullPointerException("Salt must not be null nor empty.");
        if(verifier == null || verifier.trim().equals("")) throw new NullPointerException("Verifier must not be null nor empty.");

        BigInteger v = new BigInteger("0x" + verifier);

        BigInteger b = this.routines.generatePrivateValue();
        BigInteger k = this.routines.computeK();
        BigInteger B = this.routines.computeServerPublicValue(this.routines.parameters, k, v, b);

        this.I = identity;
        this.salt = new BigInteger("0x" + salt);
        this.verifier = v;
        this.b = b;
        this.B = B;

        return this.B.toString(16);
    }

    /**
     * Compute the session key "S" without computing or checking client evidence
     * @param A Client public key "A"
     */
    private BigInteger sessionKey(BigInteger A) {
        if(A == null) throw new NullPointerException("Client public value (A) must not be null.");

        if (!this.routines.isValidPublicValue(A))
            throw new IllegalArgumentException("Invalid Client public value (A): " + A.toString(16));

        BigInteger u = this.routines.computeU(A, this.B);

        // S
        return this.routines.computeServerSessionKey(this.routines.parameters.primeGroup.N, this.verifier, u, A, this.b);
    }

    /**
     * Computes M2 and checks if client is authenticated.
     * @param A Client public key "A"
     * @param M1 Client message "M1"
     * @return M2 The server evidence message
     */
    public String step2(String A, String M1) {
        if(A == null || A.trim().equals("")) throw new NullPointerException("Client public key (A) must not be null nor empty.");
        if (M1 == null || M1.trim().equals("")) throw new Error("Client evidence (M1) must not be null nor empty.");

        BigInteger Abi = new BigInteger("0x" + A);
        BigInteger M1bi = new BigInteger("0x" + M1);

        BigInteger S = this.sessionKey(Abi);

        BigInteger computedM1 = this.routines.computeClientEvidence(this.I, this.salt, Abi, this.B, S);
        if (!Objects.equals(computedM1, M1bi)) throw new Error("Bad client credentials.");

        // M2
        return this.routines.computeServerEvidence(Abi, M1bi, S).toString(16);
    }

    /**
     * Exports identity, salt, verifier, b and B values.
     * Should be called after step1.
     */
    public ServerState toState() {
        return new ServerState(
                this.I,
                this.salt.toString(16),
                this.verifier.toString(16),
                this.B.toString(16),
                this.b.toString(16)
        );
    }

    /**
     * Generates Server session from existing values: identity, salt, verifier, b and B.
     * @param routines The routines used when server session first generated.
     * @param state The state object, usually can be accessed from toJSON().
     */
    public static Server fromState(Routines routines, ServerState state) {
        Server srv = new Server(routines);

        srv.I = state.identity;
        srv.salt = new BigInteger("0x" + state.salt);
        srv.verifier = new BigInteger("0x" + state.verifier);
        srv.b = new BigInteger("0x" + state.b);
        srv.B = new BigInteger("0x" + state.B);

        return srv;
    }
}
