package com.project_christopher.libraries.srp.Components;

import java.util.Map;

public class CompatibleCrypto {
    public Map<String, HashFunction> hashFunctions;
    public RandomBytes randomBytes;

    public CompatibleCrypto(Map<String, HashFunction> hashFunctions, RandomBytes randomBytes) {
        this.hashFunctions = hashFunctions;
        this.randomBytes = randomBytes;
    }
}
