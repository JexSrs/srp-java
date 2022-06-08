package gr.jexsrs.srp.Components;

public interface HashFunction {
    byte[] call(byte[] data);
}
