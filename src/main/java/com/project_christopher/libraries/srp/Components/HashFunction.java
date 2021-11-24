package com.project_christopher.libraries.srp.Components;

import java.nio.Buffer;

public interface HashFunction {
    Buffer[] call(Buffer[] data);
}
