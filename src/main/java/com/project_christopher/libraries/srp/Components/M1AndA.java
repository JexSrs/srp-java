package com.project_christopher.libraries.srp.Components;

import org.json.JSONObject;

public class M1AndA {
    String A, M1;

    public M1AndA(String a, String m1) {
        A = a;
        M1 = m1;
    }

    public JSONObject toJSON() {
        return new JSONObject()
                .put("A", this.A)
                .put("M1", this.M1);
    }
}
