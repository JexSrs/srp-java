package gr.jexsrs.srp.Components;

public class ClientState {

    public String identity, A, a, M1, S;
    public byte[] IH;

    public ClientState(String identity, byte[] IH, String A, String a, String M1, String S) {
        this.identity = identity;
        this.IH = IH;
        this.A = A;
        this.a = a;
        this.M1 = M1;
        this.S = S;
    }
}
