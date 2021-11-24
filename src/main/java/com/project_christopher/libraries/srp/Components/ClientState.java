package com.project_christopher.libraries.srp.Components;

import org.json.JSONArray;
import org.json.JSONObject;

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

    public JSONObject toJSON() {
        return new JSONObject()
                .put("identity", this.identity)
                .put("IH", this.IH)
                .put("A", this.A)
                .put("a", this.a)
                .put("M1", this.M1)
                .put("S", this.S);
    }

    private static byte[] jsonArrayToByteArray(JSONArray array) {
        int length = array.length();
        byte[] r = new byte[length];

        for (int i = 0; i < length; i++)
            r[i] = (byte)array.getInt(i);

        return r;
    }

    public static ClientState fromJSON(JSONObject json) {
        return new ClientState(
                json.has("identity") ? json.getString("identity") : "",
                json.has("IH") ? jsonArrayToByteArray(json.getJSONArray("IH")) : new byte[0],
                json.has("A") ? json.getString("A") : "",
                json.has("a") ? json.getString("a") : "",
                json.has("M1") ? json.getString("M1") : "",
                json.has("S") ? json.getString("S") : ""
        );
    }

    public static ClientState fromJSON(String json) {
        return fromJSON(new JSONObject(json));
    }
}
