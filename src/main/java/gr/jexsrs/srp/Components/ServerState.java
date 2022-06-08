package gr.jexsrs.srp.Components;


public class ServerState {

    public String identity, salt, verifier, b, B;

    public ServerState(String identity, String salt, String verifier, String B, String b) {
        this.identity = identity;
        this.salt = salt;
        this.verifier = verifier;
        this.B = B;
        this.b = b;
    }
}
