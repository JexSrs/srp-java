package com.project_christopher.libraries.srp.Components;

import org.json.JSONObject;

public class ServerState {

    public String identity, salt, verifier, b, B;

    public ServerState(String identity, String salt, String verifier, String B, String b) {
        this.identity = identity;
        this.salt = salt;
        this.verifier = verifier;
        this.B = B;
        this.b = b;
    }

    public JSONObject toJSON() {
        return new JSONObject()
                .put("identity", this.identity)
                .put("salt", this.salt)
                .put("verifier", this.verifier)
                .put("B", this.B)
                .put("b", this.b);
    }

    public static ServerState fromJSON(JSONObject json) {
        return new ServerState(
                json.has("identity") ? json.getString("identity") : "",
                json.has("salt") ? json.getString("salt") : "",
                json.has("verifier") ? json.getString("verifier") : "",
                json.has("B") ? json.getString("B") : "",
                json.has("b") ? json.getString("b") : ""
        );
    }

    public static ServerState fromJSON(String json) {
        return fromJSON(new JSONObject(json));
    }
}
