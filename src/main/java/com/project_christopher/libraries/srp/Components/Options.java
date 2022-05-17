package com.project_christopher.libraries.srp.Components;

import com.project_christopher.libraries.srp.Modules.Routines;

public class Options {
    public PrimeGroup primeGroup;
    public HashFunction hashFunction;
    public Routines routines;
    public ServerState srvState;
    public ClientState clientState;

    public Options() {
    }

    public Options(PrimeGroup pg, HashFunction hf) {
        this.primeGroup = pg;
        this.hashFunction = hf;
    }

    public Options(PrimeGroup pg, HashFunction hf, Routines routines) {
        this.primeGroup = pg;
        this.hashFunction = hf;
        this.routines = routines;
    }

    public Options(PrimeGroup pg, HashFunction hf, ServerState srvState) {
        this.primeGroup = pg;
        this.hashFunction = hf;
        this.srvState = srvState;
    }

    public Options(PrimeGroup pg, HashFunction hf, ClientState clientState) {
        this.primeGroup = pg;
        this.hashFunction = hf;
        this.clientState = clientState;
    }

    public Options(PrimeGroup pg, HashFunction hf, Routines routines, ServerState srvState) {
        this.primeGroup = pg;
        this.hashFunction = hf;
        this.routines = routines;
        this.srvState = srvState;
    }

    public Options(PrimeGroup pg, HashFunction hf, Routines routines, ClientState clientState) {
        this.primeGroup = pg;
        this.hashFunction = hf;
        this.routines = routines;
        this.clientState = clientState;
    }
}
