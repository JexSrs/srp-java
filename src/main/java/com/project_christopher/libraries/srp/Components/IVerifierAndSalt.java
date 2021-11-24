package com.project_christopher.libraries.srp.Components;

import org.json.JSONObject;

public class IVerifierAndSalt {
    public String verifier, salt;

    public IVerifierAndSalt(String verifier, String salt) {
        this.verifier = verifier;
        this.salt = salt;
    }

    public JSONObject toJSON() {
        return new JSONObject()
                .put("verifier", this.verifier)
                .put("salt", this.salt);
    }
}
