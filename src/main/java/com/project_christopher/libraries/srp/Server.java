package com.project_christopher.libraries.srp;

import com.project_christopher.libraries.srp.Components.ServerState;
import com.project_christopher.libraries.srp.Exceptions.BadClientCredentials;
import com.project_christopher.libraries.srp.Modules.Routines;

import java.math.BigInteger;
import java.util.Objects;

public class Server {

    private final Routines routines;

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
        if(identity == null || identity.trim().equals("")) throw new IllegalArgumentException("User's identity (I) must not be null nor empty.");
        if(salt == null || salt.trim().equals("")) throw new IllegalArgumentException("Salt (s) must not be null nor empty.");
        if(verifier == null || verifier.trim().equals("")) throw new IllegalArgumentException("Verifier (v) must not be null nor empty.");

        BigInteger v = new BigInteger(verifier, 16);

        BigInteger b = this.routines.generatePrivateValue();
        BigInteger k = this.routines.computeK();
        BigInteger B = this.routines.computeServerPublicValue(k, v, b);

        this.I = identity;
        this.salt = new BigInteger(salt, 16);
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
        if(A == null) throw new IllegalArgumentException("Client's public value (A) must not be null.");

        if (!this.routines.isValidPublicValue(A))
            throw new IllegalArgumentException("Invalid client's public value (A): " + A.toString(16));

        BigInteger u = this.routines.computeU(A, this.B);

        // S
        return this.routines.computeServerSessionKey(this.verifier, u, A, this.b);
    }

    /**
     * Computes M2 and checks if client is authenticated.
     * @param A Client public key "A"
     * @param M1 Client message "M1"
     * @return M2 The server evidence message
     */
    public String step2(String A, String M1) throws BadClientCredentials {
        if(A == null || A.trim().equals("")) throw new IllegalArgumentException("Client public value (A) must not be null nor empty.");
        if (M1 == null || M1.trim().equals("")) throw new IllegalArgumentException("Client evidence (M1) must not be null nor empty.");

        BigInteger Abi = new BigInteger(A, 16);
        BigInteger M1bi = new BigInteger(M1, 16);

        BigInteger S = this.sessionKey(Abi);

        BigInteger computedM1 = this.routines.computeClientEvidence(this.I, this.salt, Abi, this.B, S);
        if (!Objects.equals(computedM1, M1bi)) throw new BadClientCredentials("Bad client credentials.");

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

        // filled after step1
        srv.I = state.identity;
        srv.salt = new BigInteger(state.salt, 16);
        srv.verifier = new BigInteger(state.verifier, 16);
        srv.b = new BigInteger(state.b, 16);
        srv.B = new BigInteger(state.B, 16);

        return srv;
    }
}
