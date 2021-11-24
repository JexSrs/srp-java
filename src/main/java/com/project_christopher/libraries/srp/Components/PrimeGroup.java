package com.project_christopher.libraries.srp.Components;

import java.math.BigInteger;

public class PrimeGroup {
    public BigInteger N;
    public BigInteger g;

    public PrimeGroup(BigInteger N, BigInteger G) {
        this.N = N;
        this.g = G;
    }
}
