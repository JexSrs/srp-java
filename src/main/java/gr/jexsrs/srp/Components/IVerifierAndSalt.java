package gr.jexsrs.srp.Components;


public class IVerifierAndSalt {
    public String verifier, salt;

    public IVerifierAndSalt(String verifier, String salt) {
        this.verifier = verifier;
        this.salt = salt;
    }
}
